package com.devproject.controler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.devproject.jsf.util.Constantes;
import com.devproject.jsf.util.FacesUtil;
import com.devproject.model.Funcao;
import com.devproject.model.Habilidade;
import com.devproject.model.Projeto;
import com.devproject.model.Usuario;
import com.devproject.service.ProjetoService;

@ManagedBean(name="projetos")
@ViewScoped
public class ProjetosBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{projetoService}")
	private ProjetoService projetoService;
	
	
	@ManagedProperty(value="#{login.usuario.usuario}")
	private Usuario usuario;

	private List<Projeto> projetosBusca;
	private List<Projeto> meusProjetos;
	private List<Projeto> projetosParticipantes;
	private List<Projeto> projetosRecomendados;
	private Funcao[] funcoes = Constantes.FUNCOES_SOLICITAVEIS;
	private List<Habilidade> habilidadesSelecionadas;
	private final Habilidade[] habilidades = Habilidade.values();
	private String busca;
	
	public void inicializar(){
		if(projetosBusca == null){
			projetosRecomendados = projetoService.buscarProjetosRecomendados(usuario);
			meusProjetos = projetoService.buscarMeusProjetos(usuario);
			projetosParticipantes = projetoService.buscarProjetosParticipantes(usuario);
			projetosBusca = projetosRecomendados;
			String finalizado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("finalizado");
			String titulo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("titulo");
			if(finalizado != null && titulo != null && "true".equals(finalizado)){
				FacesUtil.adicionaMensagemInfo("Projeto finalizado.", "O projeto "+titulo+" foi finalizado com sucesso.");
			}
		}
	}
	
	public void inicializarNaoLogado(){
		if(projetosBusca == null){
			habilidadesSelecionadas = new ArrayList<Habilidade>();
			projetosBusca = projetoService.buscarProjetosBrowser("",habilidadesSelecionadas);
		}
	}
	
	public void pesquisarNaoLogado(){
		projetosBusca = projetoService.buscarProjetosBrowser(busca,habilidadesSelecionadas);
	}
	
	public void pesquisar(){
		projetosBusca = projetoService.buscarProjetosBrowser(busca,habilidadesSelecionadas);
	}
	
	public void porParticipantes(){
		projetosBusca = projetosParticipantes;
	}
	
	public void porRecomendados(){
		projetosBusca = projetosRecomendados;
	}
	
	public void porMeusProjetos(){
		projetosBusca = meusProjetos;
	}
	
	public void visualizarProjeto(){
		Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		FacesUtil.redirect("/sistema/projeto.xhtml?id="+id);
	}
	
	public List<Projeto> getProjetosBusca() {
		return projetosBusca;
	}
	public void setProjetosBusca(List<Projeto> projetosBusca) {
		this.projetosBusca = projetosBusca;
	}
	public List<Projeto> getMeusProjetos() {
		return meusProjetos;
	}
	public void setMeusProjetos(List<Projeto> meusProjetos) {
		this.meusProjetos = meusProjetos;
	}
	public List<Projeto> getProjetosParticipantes() {
		return projetosParticipantes;
	}
	public void setProjetosParticipantes(List<Projeto> projetosParticipantes) {
		this.projetosParticipantes = projetosParticipantes;
	}
	public List<Habilidade> getHabilidadesSelecionadas() {
		return habilidadesSelecionadas;
	}
	public void setHabilidadesSelecionadas(List<Habilidade> habilidadesSelecionadas) {
		this.habilidadesSelecionadas = habilidadesSelecionadas;
	}
	public String getBusca() {
		return busca;
	}
	public void setBusca(String busca) {
		this.busca = busca;
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

	public ProjetoService getProjetoService() {
		return projetoService;
	}

	public void setProjetoService(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	public List<Projeto> getProjetosRecomendados() {
		return projetosRecomendados;
	}

	public void setProjetosRecomendados(List<Projeto> projetosRecomendados) {
		this.projetosRecomendados = projetosRecomendados;
	}
	
	public Funcao[] getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(Funcao[] funcoes) {
		this.funcoes = funcoes;
	}
}
