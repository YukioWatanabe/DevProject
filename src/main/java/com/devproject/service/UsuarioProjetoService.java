package com.devproject.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devproject.dao.UsuarioProjetoDao;
import com.devproject.model.Projeto;
import com.devproject.model.Usuario;
import com.devproject.model.UsuarioProjeto;
import com.devproject.model.UsuarioProjetoKey;

@Service
public class UsuarioProjetoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UsuarioProjetoDao dao;
	
	public UsuarioProjeto buscarPeloId(UsuarioProjeto usuarioProjeto){
		return dao.buscarPeloId(usuarioProjeto);
	}
	
	public UsuarioProjeto salvar(UsuarioProjeto usuarioProjeto){
		return dao.salvar(usuarioProjeto);
	}
	
	public UsuarioProjeto atualizar(UsuarioProjeto usuarioProjeto){
		return dao.atualizar(usuarioProjeto);
	}
	
	public void deletarUsuarioProjeto(Usuario usuario, Projeto projeto){
		UsuarioProjetoKey keys = new UsuarioProjetoKey();

		keys.setUsuario(usuario);
		keys.setProjeto(projeto);
		UsuarioProjeto usuarioProjeto = new UsuarioProjeto();
		usuarioProjeto.setPk(keys);
		dao.deletarUsuarioProjeto(usuarioProjeto);
	}
	
	public UsuarioProjeto buscarProjetoTela(Long id, Usuario usuario){
		UsuarioProjeto retorno;
		retorno = dao.buscarProjetoTela(id,usuario);
		if(retorno != null){
			return retorno;
		}else{
			throw new NoResultException();
		}
	}

	public List<UsuarioProjeto> buscarUsuariosDoProjeto(Projeto projeto) {
		return dao.buscarUsuariosDoProjeto(projeto);
	}
	
}