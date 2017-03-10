package com.devproject.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devproject.model.Funcao;
import com.devproject.model.Habilidade;
import com.devproject.model.Iteracao;
import com.devproject.model.Projeto;
import com.devproject.model.Usuario;
import com.devproject.model.UsuarioProjeto;

@Repository
@Transactional
public class ProjetoDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public Projeto salvar(@Valid Projeto projeto){
		manager.persist(projeto);
		return projeto;
	}
	
	public Projeto atualizar(Projeto projeto) {
		manager.merge(projeto);
		return projeto;
	}
	
	public void deletar(Projeto projeto){
		manager.remove(projeto);
	}
	
	public Projeto buscarProjetoPorId(Long id){
		Projeto projeto;
		projeto = manager.find(Projeto.class, id);
		Hibernate.initialize(projeto.getIteracoes());
		Hibernate.initialize(projeto.getRequisitos());
		return projeto;
	}

	public void salvarUsuarioProjeto(UsuarioProjeto usuarioProjeto){
		manager.persist(usuarioProjeto);
	}

	public List<Projeto> buscarProjetosRecomendados(Usuario usuario) {
		boolean possuiHabilidade = usuario.getHabilidades() != null && !usuario.getHabilidades().isEmpty();
		StringBuilder query = new StringBuilder();
		
		query.append("select p from Projeto p left join fetch p.requisitos req ");
		query.append("where p not in ( select p.id from Projeto p join p.usuarios up ");
						 query.append("where up.pk.usuario = :usuario)");
		if(possuiHabilidade){
			query.append("and p.id in (select p.id from Projeto p join p.requisitos req where req in :habilidades) ");
		}
		if(possuiHabilidade){
			return manager.createQuery(query.toString(), Projeto.class)
					.setParameter("usuario", usuario)
					.setParameter("habilidades", usuario.getHabilidades())
					.setMaxResults(100)
					.getResultList();
		}else{
			return manager.createQuery(query.toString(), Projeto.class)
					.setParameter("usuario", usuario)
					.setMaxResults(100)
					.getResultList();
		}
	}

	public List<Projeto> buscarMeusProjetos(Usuario usuario) {
		StringBuilder query = new StringBuilder();
		query.append("select p from Projeto p left join fetch p.requisitos req join fetch p.usuarios up ");
		query.append("where up.pk.usuario = :usuario ");
		query.append("and up.funcao = :funcao");
		return manager.createQuery(query.toString(), Projeto.class)
				  .setParameter("usuario", usuario)
				  .setParameter("funcao", Funcao.CRIADOR)
				  .setMaxResults(100)
				  .getResultList();
	}

	public List<Projeto> buscarProjetosParticipantes(Usuario usuario) {
		StringBuilder query = new StringBuilder();
		query.append("select p from Projeto p left join fetch p.requisitos req join fetch p.usuarios up ");
		query.append("where up.pk.usuario = :usuario ");
		query.append("and up.funcao != :funcao ");
		query.append("and up.funcao != :banido");
		return manager.createQuery(query.toString(), Projeto.class)
				  .setParameter("usuario", usuario)
				  .setParameter("funcao", Funcao.CRIADOR)
				  .setParameter("banido", Funcao.BANIDO)
				  .setMaxResults(100)
				  .getResultList();
	}

	public List<Projeto> buscarProjetosBrowser(String busca, List<Habilidade> habilidades) {
		List<Projeto> projetos;
		StringBuilder query = new StringBuilder();
		query.append("select p from Projeto p left join fetch p.requisitos req ");
		query.append("where lower(p.titulo) like lower(:busca) ");
		if(habilidades.size() != 0){
			query.append("and p.id in (select p.id from Projeto p join p.requisitos req where req in :habilidades) ");
			projetos = manager.createQuery(query.toString(), Projeto.class)
					  .setParameter("busca", "%"+busca+"%")
					  .setParameter("habilidades", habilidades)
					  .setMaxResults(100)
					  .getResultList();
			for(Projeto projeto : projetos){
				Hibernate.initialize(projeto.getRequisitos());
			}
			return projetos;
		}else{
			return manager.createQuery(query.toString(), Projeto.class)
					  .setParameter("busca", "%"+busca+"%")
					  .setMaxResults(100)
					  .getResultList();
		}
	}

	public Projeto deletarIteracao(Projeto projeto, Iteracao iteracaoSelecionada) {
		iteracaoSelecionada = manager.find(Iteracao.class,iteracaoSelecionada.getId());
		manager.remove(iteracaoSelecionada);
		projeto.getIteracoes().remove(iteracaoSelecionada);
		return projeto;
	}
	
}