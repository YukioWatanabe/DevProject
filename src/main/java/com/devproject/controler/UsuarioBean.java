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

import com.devproject.jsf.util.FacesUtil;
import com.devproject.model.Habilidade;
import com.devproject.model.SolicitacaoAmizade;
import com.devproject.model.SolicitacaoAmizadeKey;
import com.devproject.model.Usuario;
import com.devproject.service.SolicitacaoService;
import com.devproject.service.UsuarioService;

@ManagedBean(name="usuario")
@ViewScoped
public class UsuarioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{usuarioService}")
	private UsuarioService usuarioService;
	
	private Usuario usuario;
	
	@ManagedProperty(value="#{login.usuario.usuario}")
	private Usuario usuarioLogado;
	
	@ManagedProperty(value="#{solicitacaoService}")
	private SolicitacaoService solicitacaoService;
	
	private Habilidade[] habilidades = Habilidade.values();
	
	private List<Habilidade> habilidadeSelecionada;
	
	private List<Habilidade> habilidadesDoUsuario;
	
	private SolicitacaoAmizade solicitacao;
	
	private boolean jaSolicitou;
	
	private boolean jaAmigo;
	
	public void inicializarPerfil(){
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if(id != null && Long.parseLong(id) != usuarioLogado.getId()){
			usuario = usuarioService.buscarPeloId(Long.parseLong(id));
			jaSolicitou = solicitacaoService.buscaSolicitacaoAmizadePorId(usuarioLogado, usuario) != null || solicitacaoService.buscaSolicitacaoAmizadePorId(usuario, usuarioLogado) != null;
			jaAmigo = usuarioService.verificaAmizadeExiste(usuario, usuarioLogado);
		}else{
			usuario = usuarioLogado;
		}
	}
	
	public void gravarFoto(FileUploadEvent file){
		usuario.setFoto(file.getFile().getContents());
	}
	
	public void cadastrar(){
		try{
			usuarioService.buscarPeloUsername(usuario.getUsername());
			FacesUtil.adicionaMensagemAviso("Username já em uso.","O username informado já está em uso.");
		}catch(NoResultException e){
			usuarioService.cadastrar(usuario);
			FacesUtil.redirect("/index.xhtml?sucesso=true&usuario="+usuario.getNome());
		}
	}
	
    public void inicializarEdicao() {
       this.usuario = usuarioLogado;
       this.habilidadesDoUsuario = new ArrayList<Habilidade>();
       this.habilidadesDoUsuario.addAll(usuarioLogado.getHabilidades());
    }
    
    public void inicializarNovo() {
    	usuario = new Usuario();
     }
	
	public void salvar(){
		this.usuario.setHabilidades(habilidadesDoUsuario);
		this.usuarioService.atualizar(usuario);
		FacesUtil.adicionaMensagemInfo("Alterado com sucesso!", "As informações do seu perfil foram atualizados");
	}
	
	public void deletarHabilidade(){
		this.habilidadesDoUsuario.removeAll(habilidadeSelecionada);
	}
	
	
	public void mostrarDialog(){
		solicitacao = new SolicitacaoAmizade();
		solicitacao.setPk(new SolicitacaoAmizadeKey());
		solicitacao.getPk().setReceptor(usuario);
		solicitacao.getPk().setSolicitante(usuarioLogado);
	}
	
	public void solicitarAmizade(){
		solicitacao.setDataSolicitacao(new Date());
		solicitacao = solicitacaoService.salvarSolicitacaoAmizade(solicitacao);
		FacesUtil.adicionaMensagemInfo("Solicitação enviada", 
									   "Solicitação de amizade enviada para o usuário "+solicitacao.getPk().getReceptor().getNome());
		jaSolicitou = true;
	}
	
	public Date getDataAtual(){
		return new Date();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Habilidade[] getHabilidades() {
		return habilidades;
	}
	public void setHabilidades(Habilidade[] habilidades) {
		this.habilidades = habilidades;
	}

	public List<Habilidade> getHabilidadeSelecionada() {
		return habilidadeSelecionada;
	}
	public void setHabilidadeSelecionada(List<Habilidade> habilidadeSelecionada) {
		this.habilidadeSelecionada = habilidadeSelecionada;
	}

	public List<Habilidade> getHabilidadesDoUsuario() {
		return habilidadesDoUsuario;
	}

	public void setHabilidadesDoUsuario(List<Habilidade> habilidadesDoUsuario) {
		this.habilidadesDoUsuario = habilidadesDoUsuario;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public SolicitacaoService getSolicitacaoService() {
		return solicitacaoService;
	}

	public void setSolicitacaoService(SolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	public SolicitacaoAmizade getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoAmizade solicitacao) {
		this.solicitacao = solicitacao;
	}

	public boolean isJaSolicitou() {
		return jaSolicitou;
	}

	public void setJaSolicitou(boolean jaSolicitou) {
		this.jaSolicitou = jaSolicitou;
	}

	public boolean isJaAmigo() {
		return jaAmigo;
	}

	public void setJaAmigo(boolean jaAmigo) {
		this.jaAmigo = jaAmigo;
	}
}
