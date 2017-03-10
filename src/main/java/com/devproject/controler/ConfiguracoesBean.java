package com.devproject.controler;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.devproject.jsf.util.FacesUtil;
import com.devproject.jsf.util.TemaService;
import com.devproject.model.Tema;
import com.devproject.model.Usuario;
import com.devproject.service.UsuarioService;

@ManagedBean(name="config")
@ViewScoped
public class ConfiguracoesBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{usuarioService}")
	private UsuarioService usuarioService;
	
	@ManagedProperty(value="#{login.usuario.usuario}")
	private Usuario usuario;
	
	@ManagedProperty(value="#{TemaService}")
	private TemaService temaService;
	
	private List<Tema> temas;
	
	public void inicializar(){
		temas = temaService.getTemas();
	}
	
	public void salvar(){
		usuarioService.atualizar(usuario);
		FacesUtil.adicionaMensagemInfo("Configurações salvas.", "As suas configurações foram salvas.");
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TemaService getTemaService() {
		return temaService;
	}

	public void setTemaService(TemaService temaService) {
		this.temaService = temaService;
	}

	public List<Tema> getTemas() {
		return temas;
	}

	public void setTemas(List<Tema> temas) {
		this.temas = temas;
	}
	
}
