package com.devproject.controler;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import com.devproject.jsf.util.Constantes;
import com.devproject.jsf.util.FacesUtil;
import com.devproject.model.Funcao;
import com.devproject.model.Iteracao;
import com.devproject.model.Tarefa;
import com.devproject.model.Usuario;
import com.devproject.model.UsuarioProjeto;
import com.devproject.service.IteracaoService;
import com.devproject.service.TarefaService;
import com.devproject.service.UsuarioProjetoService;

@ManagedBean(name="iteracao")
@ViewScoped
public class IteracaoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{iteracaoService}")
	private IteracaoService iteracaoService;
	
	@ManagedProperty(value="#{usuarioProjetoService}")
	private UsuarioProjetoService usuarioProjetoService;
	
	@ManagedProperty(value="#{tarefaService}")
	private TarefaService tarefaService;
	
	@ManagedProperty(value="#{login.usuario.usuario}")
	private Usuario usuario;
	
	private Iteracao iteracao;
	
	private Tarefa tarefaSelecionada;
	
	private UsuarioProjeto usuarioProjeto;
	
	private boolean permitidoIteracao;
	
	public void inicializarNovo(){
		Long idProjeto = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idproj"));
		iteracao = new Iteracao();
		try{
			usuarioProjeto = usuarioProjetoService.buscarProjetoTela(idProjeto, usuario);
			setPermissao();
			if(!permitidoIteracao){
				FacesUtil.redirect("/sistema/acesso-negado.xhtml");
			}
		}
		catch(NoResultException e){
			FacesUtil.redirect("/sistema/acesso-negado.xhtml");
		}
	}
	
	public void inicializarVisualizacao(){
		inicializarGeral(Constantes.VISUALIZAR);
		boolean cadastrado = "true".equals(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cadastrado"));
		if(cadastrado){
			FacesUtil.adicionaMensagemInfo("Iteração cadastrada com sucesso!", "Iteração "+iteracao.getTitulo()+" cadastrada com sucesso.");
		}
	}

	public void inicializarEditar() {
		inicializarGeral(Constantes.EDITAR);
	}

	private void inicializarGeral(int modo) {
		Long idIteracao = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		try{
			iteracao = Constantes.EDITAR == modo ? iteracaoService.buscarIteracaoTelaEdit(idIteracao, usuario) : iteracaoService.buscarIteracaoTela(idIteracao, usuario);
			usuarioProjeto = usuarioProjetoService.buscarProjetoTela(iteracao.getProjeto().getId(), usuario);
			setPermissao();
			if(Constantes.EDITAR == modo && !permitidoIteracao){
				FacesUtil.redirect("/sistema/acesso-negado.xhtml");
			}
		}
		catch(NoResultException e){
			FacesUtil.redirect("/sistema/acesso-negado.xhtml");
		}
	}

	private void setPermissao() {
		for(Funcao funcao : Constantes.PERMISSOES_EDITAR_SCRUM){
			if(funcao.equals(usuarioProjeto.getFuncao())){
				permitidoIteracao = true;
			}
		}
	}
	
	public void cadastrar(){
		this.iteracao.setProjeto(usuarioProjeto.getPk().getProjeto());
		iteracao = iteracaoService.salvar(iteracao);
		FacesUtil.redirect("/sistema/iteracao.xhtml?id="+iteracao.getId()+"&cadastro=true");
	}
	
	public void atualizar(){
		iteracaoService.alterar(iteracao);
		FacesUtil.redirect("/sistema/iteracao.xhtml?id="+iteracao.getId());
	}
	
	public void deletarTarefa(){
		if(tarefaSelecionada != null){
			tarefaService.deletar(tarefaSelecionada);
			FacesUtil.adicionaMensagemInfo("Tarefa deletada!", "Tarefa "+tarefaSelecionada.getTitulo()+" deletada.");
			iteracao.getTarefas().remove(tarefaSelecionada);
		}else{
			FacesUtil.adicionaMensagemAviso("Por favor, selecione uma tarefa.", "Para deletar uma tarefa, é necessário seleciona-la.");
		}
	}
	
	public void editarTarefa(){
		if(tarefaSelecionada != null){
			FacesUtil.redirect("/sistema/tarefa/editar-tarefa.xhtml?id="+tarefaSelecionada.getId());
		}else{
			FacesUtil.adicionaMensagemAviso("Por favor, selecione uma tarefa.", "Para editar uma tarefa, é necessário seleciona-la.");
		}
	}
	
	public void visualizarTarefa(){
		if(tarefaSelecionada != null){
			FacesUtil.redirect("/sistema/tarefa.xhtml?id="+tarefaSelecionada.getId());
		}else{
			FacesUtil.adicionaMensagemAviso("Por favor, selecione uma tarefa.", "Para visualizar uma tarefa, é necessário seleciona-la.");
		}
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

	public Iteracao getIteracao() {
		return iteracao;
	}

	public void setIteracao(Iteracao iteracao) {
		this.iteracao = iteracao;
	}

	public IteracaoService getIteracaoService() {
		return iteracaoService;
	}

	public void setIteracaoService(IteracaoService iteracaoService) {
		this.iteracaoService = iteracaoService;
	}

	public Tarefa getTarefaSelecionada() {
		return tarefaSelecionada;
	}

	public void setTarefaSelecionada(Tarefa tarefaSelecionada) {
		this.tarefaSelecionada = tarefaSelecionada;
	}
	
	public UsuarioProjetoService getUsuarioProjetoService() {
		return usuarioProjetoService;
	}


	public void setUsuarioProjetoService(UsuarioProjetoService usuarioProjetoService) {
		this.usuarioProjetoService = usuarioProjetoService;
	}


	public UsuarioProjeto getUsuarioProjeto() {
		return usuarioProjeto;
	}


	public void setUsuarioProjeto(UsuarioProjeto usuarioProjeto) {
		this.usuarioProjeto = usuarioProjeto;
	}


	public boolean isPermitidoIteracao() {
		return permitidoIteracao;
	}


	public void setPermitidoIteracao(boolean permitidoIteracao) {
		this.permitidoIteracao = permitidoIteracao;
	}


	public TarefaService getTarefaService() {
		return tarefaService;
	}


	public void setTarefaService(TarefaService tarefaService) {
		this.tarefaService = tarefaService;
	}
	
}
