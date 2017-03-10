package com.devproject.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devproject.model.Projeto;
import com.devproject.model.Usuario;
import com.devproject.model.UsuarioProjeto;

@Repository
@Transactional
public class UsuarioProjetoDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public UsuarioProjeto buscarPeloId(UsuarioProjeto usuarioProjeto) {
		return manager.find(UsuarioProjeto.class, usuarioProjeto.getPk());
	}
	
	public UsuarioProjeto salvar(UsuarioProjeto usuarioProjeto){
		manager.persist(usuarioProjeto);
		return usuarioProjeto;
	}
	
	public UsuarioProjeto atualizar(UsuarioProjeto usuarioProjeto) {
		manager.merge(usuarioProjeto);
		return usuarioProjeto;
	}
	
	public void deletarUsuarioProjeto(UsuarioProjeto usuarioProjeto){
		usuarioProjeto = manager.find(UsuarioProjeto.class, usuarioProjeto.getPk());
		manager.remove(usuarioProjeto);
	}
	
	public UsuarioProjeto getUsuarioProjeto(Usuario usuario, Projeto projeto){
		
		Long idUsuario = usuario.getId();
		Long idProjeto = projeto.getId();
		
		return manager.createQuery("from UsuarioProjeto where usuarioId = :idUsuario and projetoId = :idProjeto", UsuarioProjeto.class)
					  .setParameter("idUsuario", idUsuario)
					  .setParameter("idProjeto", idProjeto)
					  .getSingleResult();
	}
	

	public UsuarioProjeto buscarProjetoTela(Long id, Usuario usuario) {
		UsuarioProjeto projeto;
		StringBuilder query = new StringBuilder();
		query.append("select up from UsuarioProjeto up join fetch up.pk.usuario us join fetch up.pk.projeto p ");
		query.append("where (p.id = :id and us = :usuario)");
		projeto = manager.createQuery(query.toString(), UsuarioProjeto.class)
				  .setParameter("id", id)
				  .setParameter("usuario", usuario)
				  .getSingleResult();
		Hibernate.initialize(projeto.getPk().getProjeto().getIteracoes());
		Hibernate.initialize(projeto.getPk().getProjeto().getRequisitos());
		return projeto;
	}

	public List<UsuarioProjeto> buscarUsuariosDoProjeto(Projeto projeto) {
		StringBuilder query = new StringBuilder();
		query.append("select up from UsuarioProjeto up join fetch up.pk.usuario us join up.pk.projeto p ");
		query.append("where p = :projeto");
		return manager.createQuery(query.toString(), UsuarioProjeto.class)
				  .setParameter("projeto", projeto)
				  .getResultList();
	}

}