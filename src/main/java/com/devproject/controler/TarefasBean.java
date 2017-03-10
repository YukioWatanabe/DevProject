package com.devproject.controler;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.devproject.jsf.util.FacesUtil;
import com.devproject.model.Tarefa;
import com.devproject.model.Usuario;
import com.devproject.service.SolicitacaoService;
import com.devproject.service.TarefaService;

@ManagedBean(name="tarefas")
@ViewScoped
public class TarefasBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{tarefaService}")
	private TarefaService tarefaService;
	
	@ManagedProperty(value="#{solicitacaoService}")
	private SolicitacaoService solicitacaoService;
	
	@ManagedProperty(value="#{login.usuario.usuario}")
	private Usuario usuario;
	
	private boolean apenasParticipantes;
	private boolean apenasPendentes;
	private List<Tarefa> tarefasBusca;
	private String busca;
	
	public void inicializar(){
		tarefasBusca = tarefaService.buscarMinhasTarefas(usuario);
	}
	
	public void pesquisar(){
		tarefasBusca = tarefaService.buscarTarefasBrowser(busca,usuario,apenasParticipantes, apenasPendentes);
	}
	
	public void visualizarTarefa(){
		Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		FacesUtil.redirect("/sistema/tarefa.xhtml?id="+id);
	}

	public TarefaService getTarefaService() {
		return tarefaService;
	}

	public void setTarefaService(TarefaService tarefaService) {
		this.tarefaService = tarefaService;
	}

	public SolicitacaoService getSolicitacaoService() {
		return solicitacaoService;
	}

	public void setSolicitacaoService(SolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Tarefa> getTarefasBusca() {
		return tarefasBusca;
	}

	public void setTarefasBusca(List<Tarefa> tarefasBusca) {
		this.tarefasBusca = tarefasBusca;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public boolean isApenasParticipantes() {
		return apenasParticipantes;
	}

	public void setApenasParticipantes(boolean apenasParticipantes) {
		this.apenasParticipantes = apenasParticipantes;
	}
	
}
