package com.devproject.controler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.devproject.jsf.util.FacesUtil;
import com.devproject.model.Habilidade;
import com.devproject.model.Usuario;
import com.devproject.service.UsuarioService;

@ManagedBean(name="usuarios")
@ViewScoped
public class UsuariosBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{usuarioService}")
	private UsuarioService usuarioService;
	
	@ManagedProperty(value="#{login.usuario.usuario}")
	private Usuario usuario;
	
	private List<Usuario> usuarios;
	private List<Usuario> meusAmigos;
	private String busca;
	private Habilidade[] habilidades = Habilidade.values();
	private List<Habilidade> habilidadesSelecionadas = new ArrayList<Habilidade>();
	
	public void inicializar(){
		usuarios = usuarioService.buscarTodosUsuarios(usuario);
		meusAmigos = usuarioService.buscarMeusAmigos(usuario);	
	}
	
	public void inicializarNaoLogado(){
		usuarios = usuarioService.buscarTodosUsuarios();
	}
	
	public void pesquisar(){
		usuarios = usuarioService.buscarUsuariosBrowser(busca, habilidadesSelecionadas, usuario);
	}
	
	public void pesquisarNaoLogado(){
		usuarios = usuarioService.buscarUsuariosBrowser(busca, habilidadesSelecionadas);
	}
	
	public void verPerfil(){
		Long id = Long.parseLong((String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		FacesUtil.redirect("/sistema/perfil.xhtml?id="+id);
	}
	
	public void meusAmigos(){
		usuarios = meusAmigos;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public Habilidade[] getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(Habilidade[] habilidades) {
		this.habilidades = habilidades;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public List<Habilidade> getHabilidadesSelecionadas() {
		return habilidadesSelecionadas;
	}

	public void setHabilidadesSelecionadas(List<Habilidade> habilidadesSelecionadas) {
		this.habilidadesSelecionadas = habilidadesSelecionadas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getMeusAmigos() {
		return meusAmigos;
	}

	public void setMeusAmigos(List<Usuario> meusAmigos) {
		this.meusAmigos = meusAmigos;
	}
}
