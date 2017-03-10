package com.devproject.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devproject.dao.UsuarioDao;
import com.devproject.model.Habilidade;
import com.devproject.model.Projeto;
import com.devproject.model.Usuario;

@Service
public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UsuarioDao dao;

	
	public void cadastrar(@Valid Usuario usuario) {
		dao.insert(usuario);
	}
	
	public void atualizar(@Valid Usuario usuario) {
		dao.alterarUsuario(usuario);
	}
	
	public Usuario buscarPeloId(Long id) {
		return dao.buscarPeloId(id);
	}
	
	public Usuario buscarPeloUsername(String username) {
		return dao.buscarPeloUsername(username);
	}
	
	public List<Usuario> buscarTodosUsuarios(Usuario usuario) {
		return dao.buscarTodosUsuarios(usuario);
	}
	
	public List<Usuario> buscarUsuariosBrowser(String busca, List<Habilidade> habilidades, Usuario usuario) {
		return dao.buscarUsuariosBrowser(busca.toLowerCase(), habilidades, usuario);
	}
	
	public List<Usuario> buscarUsuariosDoProjeto(Projeto projeto){
		return dao.buscarUsuariosDoProjeto(projeto);
	}
	
	public Usuario salvarAmizade(Usuario solicitante, Usuario receptor){
		return dao.salvarAmizade(solicitante, receptor);
	}
	
	public boolean verificaAmizadeExiste(Usuario usuario, Usuario usuarioLogado) {
		try{
			return dao.verificaAmizadeExiste(usuario, usuarioLogado);
		}catch(NoResultException nr){
			return false;
		}
	}

	public List<Usuario> buscarMeusAmigos(Usuario usuario) {
		List<Usuario> usuarios = dao.buscarMeusAmigos(usuario);
		Set<Usuario> set = new HashSet<Usuario>(usuarios);
		usuarios = new ArrayList<Usuario>(set);
		return usuarios;
	}


	public List<Usuario> buscarTodosUsuariosParticipacao(Projeto projeto) {
		return dao.buscarTodosUsuariosParticipacao(projeto);
	}


	public List<Usuario> buscarUsuariosBrowserParticipacao(String busca, List<Habilidade> habilidades,
			Projeto projeto) {
		return dao.buscarUsuariosBrowserParticipacao(busca.toLowerCase(), habilidades, projeto);
	}

	public List<Usuario> buscarTodosUsuarios() {
		return dao.buscarTodosUsuarios();
	}

	public List<Usuario> buscarUsuariosBrowser(String busca, List<Habilidade> habilidades) {
		return dao.buscarUsuariosBrowser(busca.toLowerCase(), habilidades);
	}
}
