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

import org.primefaces.model.DualListModel;

import com.devproject.jsf.util.FacesUtil;
import com.devproject.model.EtapaTarefa;
import com.devproject.model.Iteracao;
import com.devproject.model.Projeto;
import com.devproject.model.Tarefa;
import com.devproject.model.Usuario;
import com.devproject.service.IteracaoService;
import com.devproject.service.TarefaService;
import com.devproject.service.UsuarioService;

@ManagedBean(name="tarefa")
@ViewScoped
public class TarefaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Tarefa tarefa;
	
	@ManagedProperty(value="#{tarefaService}")
	private TarefaService tarefaService;
	
	@ManagedProperty(value="#{iteracaoService}")
	private IteracaoService iteracaoService;
	
	@ManagedProperty(value="#{usuarioService}")
	private UsuarioService usuarioService;
	
	@ManagedProperty(value="#{login.usuario.usuario}")
	private Usuario usuario;
	
	private Iteracao iteracao;
	
	private DualListModel<Usuario> dlUsuario;
	
	private List<Usuario> participantes;
	
	private EtapaTarefa[] etapas = EtapaTarefa.values();
	
	public void inicializarNovo(){
		Long idIteracao = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		try{
			iteracao = iteracaoService.buscarIteracaoTela(idIteracao, usuario);
		}
		catch(NoResultException e){
			FacesUtil.redirect("/sistema/acesso-negado.xhtml");
		}
		
		tarefa = new Tarefa();
		this.tarefa.setIteracao(iteracao);
		this.tarefa.setDataInicio(new Date());
		this.tarefa.setEtapa(EtapaTarefa.NAO_REALIZADA);
		
		getPickListParticipantes(iteracao.getProjeto());
		
	}
	
	public void inicializarEditar(){
		Long idTarefa = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		try{
			tarefa = tarefaService.buscarTarefaTelaEdit(idTarefa, usuario);
			getPickListParticipantes(tarefa.getIteracao().getProjeto());
		}
		catch(NoResultException e){
			FacesUtil.redirect("/sistema/acesso-negado.xhtml");
		}
	}
	
	public void inicializarVisualizacao(){
		Long idTarefa = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		try{
			tarefa = tarefaService.buscarTarefaTela(idTarefa, usuario);
		}
		catch(NoResultException e){
			FacesUtil.redirect("/sistema/acesso-negado.xhtml");
		}
	}
	
	public void verPerfilParticipante(){
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		FacesUtil.redirect("/sistema/perfil.xhtml?id="+id);
	}

	private void getPickListParticipantes(Projeto projeto) {
		participantes = usuarioService.buscarUsuariosDoProjeto(projeto);
		dlUsuario = new DualListModel<Usuario>(new ArrayList<Usuario>(participantes), new ArrayList<Usuario>(tarefa.getParticipantes()));
		dlUsuario.getSource().removeAll(tarefa.getParticipantes());
	}
	
	public void cadastrar(){
		tarefaService.salvar(tarefa, dlUsuario.getTarget());
		FacesUtil.redirect("/sistema/iteracao.xhtml?id="+tarefa.getIteracao().getId()+"&cadastro=true");
	}
	
	public void alterar(){
		this.tarefa.setParticipantes(dlUsuario.getTarget());
		tarefaService.alterar(tarefa);
		FacesUtil.redirect("/sistema/iteracao.xhtml?id="+tarefa.getIteracao().getId());
	}
	
	public Date getDataAtual(){
		return new Date();
	}
	
	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public TarefaService getTarefaService() {
		return tarefaService;
	}

	public void setTarefaService(TarefaService tarefaService) {
		this.tarefaService = tarefaService;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public IteracaoService getIteracaoService() {
		return iteracaoService;
	}

	public void setIteracaoService(IteracaoService iteracaoService) {
		this.iteracaoService = iteracaoService;
	}

	public Iteracao getIteracao() {
		return iteracao;
	}

	public void setIteracao(Iteracao iteracao) {
		this.iteracao = iteracao;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public DualListModel<Usuario> getDlUsuario() {
		return dlUsuario;
	}

	public void setDlUsuario(DualListModel<Usuario> dlUsuario) {
		this.dlUsuario = dlUsuario;
	}

	public List<Usuario> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Usuario> participantes) {
		this.participantes = participantes;
	}

	public EtapaTarefa[] getEtapas() {
		return etapas;
	}

	public void setEtapas(EtapaTarefa[] etapas) {
		this.etapas = etapas;
	}
}
