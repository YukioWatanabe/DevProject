package com.devproject.controler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import org.primefaces.event.FileUploadEvent;

import com.devproject.jsf.util.Constantes;
import com.devproject.jsf.util.FacesUtil;
import com.devproject.model.Funcao;
import com.devproject.model.Habilidade;
import com.devproject.model.Iteracao;
import com.devproject.model.Projeto;
import com.devproject.model.SolicitacaoParticipante;
import com.devproject.model.SolicitacaoParticipanteKey;
import com.devproject.model.SolicitacaoProjeto;
import com.devproject.model.SolicitacaoProjetoKey;
import com.devproject.model.TipoProjeto;
import com.devproject.model.Usuario;
import com.devproject.model.UsuarioProjeto;
import com.devproject.model.UsuarioProjetoKey;
import com.devproject.service.ProjetoService;
import com.devproject.service.SolicitacaoService;
import com.devproject.service.UsuarioProjetoService;
import com.devproject.service.UsuarioService;

@ManagedBean(name="projeto")
@ViewScoped
public class ProjetoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{usuarioProjetoService}")
	private UsuarioProjetoService usuarioProjetoService;
	
	@ManagedProperty(value="#{projetoService}")
	private ProjetoService projetoService;
	
	@ManagedProperty(value="#{usuarioService}")
	private UsuarioService usuarioService;
	
	@ManagedProperty(value="#{login.usuario.usuario}")
	private Usuario usuario;
	
	@ManagedProperty(value="#{solicitacaoService}")
	private SolicitacaoService solicitacaoService;
	
	private Iteracao iteracaoSelecionada;
	
	private UsuarioProjeto usuarioProjeto;
	
	private Habilidade[] habilidades = Habilidade.values();
	
	private Funcao[] funcoes = Constantes.FUNCOES_SOLICITAVEIS;
	
	private TipoProjeto[] tipos = TipoProjeto.values();
	
	private List<Habilidade> habilidadeSelecionada = new ArrayList<Habilidade>();
	
	private List<Habilidade> habilidadesProjeto = new ArrayList<Habilidade>();
	
	private List<UsuarioProjeto> usuariosDoProjeto = new ArrayList<UsuarioProjeto>();
	
	private List<Usuario> usuariosDisponiveis;
	
	private List<Habilidade> habilidadesSelecionadas;
	
	private UsuarioProjeto usuarioSelecionado;
	
	private SolicitacaoProjeto solicitacaoProjeto;
	
	private SolicitacaoParticipante solicitacaoNovoParticipante;
	
	private String busca;
	
	private boolean permitidoIteracao;
	
	private boolean permitidoProjeto;
	
	private boolean jaSolicitou;
	
	public void inicializarVisualizacao(){
		Long idProjeto = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		String cadastrado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cadastrado");
		try{
			usuarioProjeto = usuarioProjetoService.buscarProjetoTela(idProjeto, usuario);
			if("true".equals(cadastrado)){
				FacesUtil.adicionaMensagemInfo("Projeto cadastrado com sucesso!",
											   "Projeto "+usuarioProjeto.getPk().getProjeto().getTitulo()+" cadastrado com sucesso.");
			}
			permitidoIteracao =  Funcao.ADMINISTRADOR.equals(usuarioProjeto.getFuncao()) || 
								 Funcao.CRIADOR.equals(usuarioProjeto.getFuncao()) ||
								 Funcao.SCRUM_MASTER.equals(usuarioProjeto.getFuncao());
			permitidoProjeto = Funcao.ADMINISTRADOR.equals(usuarioProjeto.getFuncao()) || Funcao.CRIADOR.equals(usuarioProjeto.getFuncao());
			
			solicitacaoNovoParticipante = new SolicitacaoParticipante();
			solicitacaoNovoParticipante.setPk(new SolicitacaoParticipanteKey(usuarioProjeto.getPk().getProjeto(), null));
			
			if(Funcao.BANIDO.equals(usuarioProjeto.getFuncao())){
				FacesUtil.redirect("/sistema/acesso-negado.xhtml");
			}
		}
		catch(NoResultException e){
			usuarioProjeto = new UsuarioProjeto();
			usuarioProjeto.setPk(new UsuarioProjetoKey(null, projetoService.buscarProjetoPorId(idProjeto)));
		}
		finally{
			jaSolicitou = solicitacaoService.buscaSolicitacaoProjetoPorId(usuario, usuarioProjeto.getPk().getProjeto()) != null;
			usuariosDoProjeto = usuarioProjetoService.buscarUsuariosDoProjeto(usuarioProjeto.getPk().getProjeto());
		}
	}
	
	public void inicializarEdicao(){
		if(usuarioProjeto == null){
			Long idProjeto = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
			try{
				usuarioProjeto = usuarioProjetoService.buscarProjetoTela(idProjeto, usuario);
				habilidadesProjeto.addAll(usuarioProjeto.getPk().getProjeto().getRequisitos());
				if(!Funcao.ADMINISTRADOR.equals(usuarioProjeto.getFuncao()) && !Funcao.CRIADOR.equals(usuarioProjeto.getFuncao()) ){
					FacesUtil.redirect("/sistema/acesso-negado.xhtml");
				}
			}
			catch(NoResultException e){
				FacesUtil.redirect("/sistema/acesso-negado.xhtml");
			}
		}
	}
	
	public void inicializarNovo(){
		usuarioProjeto = new UsuarioProjeto();
		usuarioProjeto.setPk(new UsuarioProjetoKey(usuario, new Projeto()));
		usuarioProjeto.setFuncao(Funcao.CRIADOR);
		usuarioProjeto.getPk().getProjeto().setDataCriacao(new Date());
		usuarioProjeto.setDataInscricao(new Date());
	}
	
	public void cadastrar(){
		usuarioProjeto.getPk().setProjeto(projetoService.salvar(usuarioProjeto.getPk().getProjeto()));
		usuarioProjeto = usuarioProjetoService.salvar(usuarioProjeto);
		FacesUtil.redirect("/sistema/projeto.xhtml?id="+usuarioProjeto.getPk().getProjeto().getId()+"&cadastrado=true");
	}
	
	
	public void atualizar(){
		Projeto projetoEditado = projetoService.atualizar(usuarioProjeto.getPk().getProjeto());
		FacesUtil.redirect("/sistema/projeto.xhtml?id="+projetoEditado.getId());
	}
	
	public void deletarHabilidade(){
		usuarioProjeto.getPk().getProjeto().getRequisitos().removeAll(habilidadeSelecionada);
	}
	
	public Date getDataAtual(){
		return new Date();
	}
	
	public void verIteracao(){
		if(iteracaoSelecionada != null){
			FacesUtil.redirect("/sistema/iteracao.xhtml?id="+iteracaoSelecionada.getId());
		}else{
			FacesUtil.adicionaMensagemAviso("Por favor, selecione uma iteração.", "Para visualizar uma iteração, é necessário seleciona-la.");
		}
	}
	
	public void editarIteracao(){
		if(iteracaoSelecionada != null){
			if(permitidoIteracao){
				FacesUtil.redirect("/sistema/iteracao/editar-iteracao.xhtml?id="+iteracaoSelecionada.getId());
			}else{
				FacesUtil.adicionaMensagemErro("Sem Permissão",
												"Para editar uma iteração, é necessário ter permissão de Administrador, Criador ou Scrum Master.");
			}
		}else{
			FacesUtil.adicionaMensagemAviso("Por favor, selecione uma iteração.", "Para editar uma iteração, é necessário seleciona-la.");
		}
	}
	
	public void deletarIteracao(){
		if(iteracaoSelecionada != null){
			if(permitidoIteracao){
				usuarioProjeto.getPk().setProjeto(projetoService.deletarIteracao(usuarioProjeto.getPk().getProjeto(),iteracaoSelecionada));
			}else{
				FacesUtil.adicionaMensagemErro("Sem Permissão",
												"Para deletar uma iteração, é necessário ter permissão de Administrador, Criador ou Scrum Master.");
			}
		}else{
			FacesUtil.adicionaMensagemAviso("Por favor, selecione uma iteração.", "Para deletar uma iteração, é necessário seleciona-la.");
		}
	}
	
	public void sairDoProjeto(){
		usuarioProjetoService.deletarUsuarioProjeto(usuarioProjeto.getPk().getUsuario(), usuarioProjeto.getPk().getProjeto());
		FacesUtil.redirect("/sistema/projetos.xhtml");
	}
	
	public void verPerfilParticipante(){
		Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		FacesUtil.redirect("/sistema/perfil.xhtml?id="+id);
	}
	
	public void mostrarDialogUsuario(){
		if(permitidoProjeto){
			Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
			usuarioSelecionado = new UsuarioProjeto();
			usuarioSelecionado.setPk(new UsuarioProjetoKey(usuarioService.buscarPeloId(id), usuarioProjeto.getPk().getProjeto()));
			usuarioSelecionado = usuarioProjetoService.buscarPeloId(usuarioSelecionado);
		}
	}
	
	
	public void mostrarDialogProjeto(){
		solicitacaoProjeto = new SolicitacaoProjeto();
		solicitacaoProjeto.setPk(new SolicitacaoProjetoKey());
		solicitacaoProjeto.getPk().setReceptor(usuarioProjeto.getPk().getProjeto());
		solicitacaoProjeto.getPk().setSolicitante(usuario);
	}
	
	public void solicitarParticipacao(){
		solicitacaoProjeto.setDataSolicitacao(new Date());
		solicitacaoProjeto = solicitacaoService.salvarSolicitacaoProjeto(solicitacaoProjeto);
		jaSolicitou = true;
		FacesUtil.adicionaMensagemInfo("Solicitação enviada", 
									   "Solicitação de participação enviada para o projeto "+solicitacaoProjeto.getPk().getReceptor().getTitulo());
	}
	
	public void editarPermissao(){
		if(permitidoProjeto){
			usuarioProjetoService.atualizar(usuarioSelecionado);
			usuariosDoProjeto = usuarioProjetoService.buscarUsuariosDoProjeto(usuarioProjeto.getPk().getProjeto());
			FacesUtil.adicionaMensagemInfo("Participante atualizado com sucesso.", "A função do usuário "+usuarioSelecionado.getPk().getUsuario().getNome()+" foi atualizada com sucesso.");
		}
	}
	
	public void banirUsuario(){
		if(permitidoProjeto){
			Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
			UsuarioProjeto usuarioBanido = new UsuarioProjeto();
			usuarioBanido.setPk(new UsuarioProjetoKey(usuarioService.buscarPeloId(id), usuarioProjeto.getPk().getProjeto()));
			usuarioBanido = usuarioProjetoService.buscarPeloId(usuarioBanido);
			usuarioBanido.setFuncao(Funcao.BANIDO);
			usuarioBanido.setDataSaida(new Date());
			usuarioProjetoService.atualizar(usuarioBanido);
			usuariosDoProjeto = usuarioProjetoService.buscarUsuariosDoProjeto(usuarioProjeto.getPk().getProjeto());
			FacesUtil.adicionaMensagemInfo("Usuário banido.", "O usuário "+usuarioBanido.getPk().getUsuario().getNome()+" foi banido.");
		}
	}
	
	public void mostrarDialogAdicionar(){
		usuariosDisponiveis = usuarioService.buscarTodosUsuariosParticipacao(usuarioProjeto.getPk().getProjeto());
	}
	
	public void pesquisar(){
		usuariosDisponiveis = usuarioService.buscarUsuariosBrowserParticipacao(busca, habilidadesSelecionadas, usuarioProjeto.getPk().getProjeto());
	}
	
	public void solicitarNovoParticipante(){
		if(permitidoProjeto){
			solicitacaoNovoParticipante.setDataSolicitacao(new Date());
			solicitacaoService.salvarSolicitacaoParticipante(solicitacaoNovoParticipante);
			FacesUtil.adicionaMensagemInfo("Solicitação enviada.", "Solicitação enviada para o usuário "+solicitacaoNovoParticipante.getPk().getReceptor().getNome()+" foi enviada com sucesso.");
		}
	}
	
	public void finalizarProjeto(){
		Projeto projeto = usuarioProjeto.getPk().getProjeto();
		projeto.setFinalizado(true);
		projetoService.atualizar(projeto);
		FacesUtil.redirect("/sistema/projetos.xhtml?finalizado=true&titulo="+projeto.getTitulo());
	}
	
	public void gravarLogo(FileUploadEvent file){
		usuarioProjeto.getPk().getProjeto().setLogo(file.getFile().getContents());
	}
	
	public UsuarioProjetoService getUsuarioProjetoService() {
		return usuarioProjetoService;
	}

	public void setUsuarioProjetoService(UsuarioProjetoService usuarioProjetoService) {
		this.usuarioProjetoService = usuarioProjetoService;
	}

	public ProjetoService getProjetoService() {
		return projetoService;
	}

	public void setProjetoService(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Iteracao getIteracaoSelecionada() {
		return iteracaoSelecionada;
	}

	public void setIteracaoSelecionada(Iteracao iteracaoSelecionada) {
		this.iteracaoSelecionada = iteracaoSelecionada;
	}

	public UsuarioProjeto getUsuarioProjeto() {
		return usuarioProjeto;
	}

	public void setUsuarioProjeto(UsuarioProjeto usuarioProjeto) {
		this.usuarioProjeto = usuarioProjeto;
	}

	public Habilidade[] getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(Habilidade[] habilidades) {
		this.habilidades = habilidades;
	}

	public TipoProjeto[] getTipos() {
		return tipos;
	}

	public void setTipos(TipoProjeto[] tipos) {
		this.tipos = tipos;
	}

	public List<Habilidade> getHabilidadeSelecionada() {
		return habilidadeSelecionada;
	}

	public void setHabilidadeSelecionada(List<Habilidade> habilidadeSelecionada) {
		this.habilidadeSelecionada = habilidadeSelecionada;
	}

	public List<Habilidade> getHabilidadesProjeto() {
		return habilidadesProjeto;
	}

	public void setHabilidadesProjeto(List<Habilidade> habilidadesProjeto) {
		this.habilidadesProjeto = habilidadesProjeto;
	}

	public boolean isPermitidoIteracao() {
		return permitidoIteracao;
	}

	public void setPermitidoIteracao(boolean permitidoIteracao) {
		this.permitidoIteracao = permitidoIteracao;
	}

	public boolean isPermitidoProjeto() {
		return permitidoProjeto;
	}

	public void setPermitidoProjeto(boolean permitidoProjeto) {
		this.permitidoProjeto = permitidoProjeto;
	}

	public List<UsuarioProjeto> getUsuariosDoProjeto() {
		return usuariosDoProjeto;
	}

	public void setUsuariosDoProjeto(List<UsuarioProjeto> usuariosDoProjeto) {
		this.usuariosDoProjeto = usuariosDoProjeto;
	}

	public UsuarioProjeto getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(UsuarioProjeto usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public Funcao[] getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(Funcao[] funcoes) {
		this.funcoes = funcoes;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public SolicitacaoProjeto getSolicitacaoProjeto() {
		return solicitacaoProjeto;
	}

	public void setSolicitacaoProjeto(SolicitacaoProjeto solicitacaoProjeto) {
		this.solicitacaoProjeto = solicitacaoProjeto;
	}

	public SolicitacaoService getSolicitacaoService() {
		return solicitacaoService;
	}

	public void setSolicitacaoService(SolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	public boolean isJaSolicitou() {
		return jaSolicitou;
	}

	public void setJaSolicitou(boolean jaSolicitou) {
		this.jaSolicitou = jaSolicitou;
	}

	public SolicitacaoParticipante getSolicitacaoNovoParticipante() {
		return solicitacaoNovoParticipante;
	}

	public void setSolicitacaoNovoParticipante(SolicitacaoParticipante solicitacaoNovoParticipante) {
		this.solicitacaoNovoParticipante = solicitacaoNovoParticipante;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public List<Usuario> getUsuariosDisponiveis() {
		return usuariosDisponiveis;
	}

	public void setUsuariosDisponiveis(List<Usuario> usuariosDisponiveis) {
		this.usuariosDisponiveis = usuariosDisponiveis;
	}

	public List<Habilidade> getHabilidadesSelecionadas() {
		return habilidadesSelecionadas;
	}

	public void setHabilidadesSelecionadas(List<Habilidade> habilidadesSelecionadas) {
		this.habilidadesSelecionadas = habilidadesSelecionadas;
	}

}
