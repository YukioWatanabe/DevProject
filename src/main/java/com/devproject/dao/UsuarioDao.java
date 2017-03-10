package com.devproject.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devproject.model.Funcao;
import com.devproject.model.Habilidade;
import com.devproject.model.Projeto;
import com.devproject.model.Usuario;

@Repository
@Transactional
public class UsuarioDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public void insert(@Valid Usuario usuario){
		manager.persist(usuario);
	}
	
	public Usuario buscarPeloUsername(String username) {
		Usuario usuario = null;
		usuario = this.manager.createQuery("from Usuario u where lower(username) = :username", Usuario.class)
				.setParameter("username", username.toLowerCase())
				.getSingleResult();
		if(usuario != null){
			Hibernate.initialize(usuario.getHabilidades());
			Hibernate.initialize(usuario.getSolicitacoesProjetoEnviadas());
		}
		return usuario;
	}

	public Usuario buscarPeloId(Long id) {
		Usuario usuario = this.manager.find(Usuario.class, id);
		Hibernate.initialize(usuario.getHabilidades());
		return usuario;
	}

	public void alterarUsuario(@Valid Usuario usuario) {
		manager.merge(usuario);
	}

	public List<Usuario> buscarTodosUsuarios(Usuario usuario) {
		return this.manager.createQuery("from Usuario u left join fetch u.habilidades where u != :usuario", Usuario.class)
				.setParameter("usuario", usuario)
				.setMaxResults(100)
				.getResultList();
	}

	public List<Usuario> buscarUsuariosBrowser(String busca, List<Habilidade> habilidades, Usuario usuario) {
		StringBuilder query = new StringBuilder("from Usuario u left join fetch u.habilidades uh ");
		query.append(" where (lower(u.nome) like :busca or lower(u.sobrenome) like :busca) ");
		query.append(" and u != :usuario ");
		if(habilidades.size() != 0){
			query.append(" and u.id in (select u.id from Usuario u join u.habilidades uh where uh in :habilidades) ");
			return setQueryBrowser(busca, query)
					.setParameter("usuario", usuario)
					.setParameter("habilidades", habilidades)
					.setMaxResults(100)
					.getResultList();
		}else{
			return 	setQueryBrowser(busca, query)
					.setParameter("usuario", usuario)
					.setMaxResults(100)
					.getResultList();
		}
	}

	private TypedQuery<Usuario> setQueryBrowser(String busca, StringBuilder query) {
		return this.manager.createQuery(query.toString(), Usuario.class)
				   .setParameter("busca", '%'+busca+'%');
	}
	
	public List<Usuario> buscarUsuariosDoProjeto(Projeto projeto){
		List<Funcao> funcoes = new ArrayList<Funcao>();
		funcoes.add(Funcao.ACOMPANHANTE);
		funcoes.add(Funcao.BANIDO);
		return manager.createQuery("select u from Usuario u join u.projetos up join up.pk.projeto p where p.id = :id and up.funcao not in :naoPermitido ", Usuario.class)
					  .setParameter("id", projeto.getId())
					  .setParameter("naoPermitido", funcoes)
					  .getResultList();
	}

	public Usuario salvarAmizade(Usuario solicitante, Usuario receptor) {
		receptor = manager.find(Usuario.class,receptor.getId());
		Hibernate.initialize(receptor.getMeusAmigos());
		receptor.getMeusAmigos().add(solicitante);
		manager.merge(receptor);
		return receptor;
	}

	public boolean verificaAmizadeExiste(Usuario usuario, Usuario usuarioLogado) {
		StringBuilder query = new StringBuilder();
		query.append("select u from Usuario u ");
		query.append("where u = :usuario ");
		
		usuarioLogado = manager.createQuery(query.toString(), Usuario.class)
					  .setParameter("usuario", usuarioLogado)
				 	  .getSingleResult();
		
		Hibernate.initialize(usuarioLogado.getAmigos());
		Hibernate.initialize(usuarioLogado.getMeusAmigos());
		return usuarioLogado.getAmigos().contains(usuario) || usuarioLogado.getMeusAmigos().contains(usuario); 
	}

	public Usuario inicializarMeusAmigos(Usuario usuario) {
		usuario = manager.find(Usuario.class, usuario.getId());
		Hibernate.initialize(usuario.getMeusAmigos());
		return usuario;
	}

	public List<Usuario> buscarMeusAmigos(Usuario usuario) {
		StringBuilder query = new StringBuilder();
		query.append("select u from Usuario u join fetch u.habilidades left join u.meusAmigos meusAmigos left join u.amigos amigos ");
		query.append("where :usuario in amigos or :usuario in meusAmigos ");
		
		return manager.createQuery(query.toString(), Usuario.class)
					  .setParameter("usuario", usuario)
					  .getResultList();
	}

	public List<Usuario> buscarTodosUsuariosParticipacao(Projeto projeto) {
		StringBuilder query = new StringBuilder("from Usuario u left join fetch u.habilidades uh ");
		query.append(" where u not in (select u from Usuario u left join u.projetos up ");
		query.append(" left join u.solicitacoesProjetoRecebidas sr ");
		query.append(" left join u.solicitacoesProjetoEnviadas se ");
		query.append(" where up.pk.projeto = :projeto ");
		query.append(" or sr.pk.solicitante = :projeto ");
		query.append(" or se.pk.receptor = :projeto) ");
		return this.manager.createQuery(query.toString(), Usuario.class)
				.setParameter("projeto", projeto)
				.setMaxResults(100)
				.getResultList();
	}

	public List<Usuario> buscarUsuariosBrowserParticipacao(String busca, List<Habilidade> habilidades,Projeto projeto) {
		StringBuilder query = new StringBuilder("from Usuario u left join fetch u.habilidades uh ");
		query.append(" where (lower(u.nome) like :busca or lower(u.sobrenome) like :busca) ");
		query.append(" and u not in (select u from Usuario u left join u.projetos up ");
		query.append(" left join u.solicitacoesProjetoRecebidas sr ");
		query.append(" left join u.solicitacoesProjetoEnviadas se ");
		query.append(" where up.pk.projeto = :projeto ");
		query.append(" or sr.pk.solicitante = :projeto ");
		query.append(" or se.pk.receptor = :projeto) ");
		if(habilidades.size() != 0){
			query.append(" and uh in :habilidades ");
			return setQueryBrowser(busca, query)
					.setParameter("projeto", projeto)
					.setParameter("habilidades", habilidades)
					.setMaxResults(100)
					.getResultList();
		}else{
			return 	setQueryBrowser(busca, query)
					.setParameter("projeto", projeto)
					.setMaxResults(100)
					.getResultList();
		}
	}

	public List<Usuario> buscarTodosUsuarios() {
		return this.manager.createQuery("from Usuario u left join fetch u.habilidades ", Usuario.class)
				.setMaxResults(100)
				.getResultList();
	}

	public List<Usuario> buscarUsuariosBrowser(String busca, List<Habilidade> habilidades) {
		StringBuilder query = new StringBuilder("from Usuario u left join fetch u.habilidades uh ");
		query.append(" where (lower(u.nome) like :busca or lower(u.sobrenome) like :busca) ");
		if(habilidades.size() != 0){
			query.append("and u.id in (select u.id from Usuario u join u.habilidades uh where uh in :habilidades) ");
			return setQueryBrowser(busca, query)
					.setParameter("habilidades", habilidades)
					.setMaxResults(100)
					.getResultList();
		}else{
			return 	setQueryBrowser(busca, query)
					.setMaxResults(100)
					.getResultList();
		}
	}

}
