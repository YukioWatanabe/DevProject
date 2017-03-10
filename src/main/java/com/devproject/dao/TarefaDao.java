package com.devproject.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devproject.jsf.util.Constantes;
import com.devproject.model.EtapaTarefa;
import com.devproject.model.Funcao;
import com.devproject.model.Projeto;
import com.devproject.model.Tarefa;
import com.devproject.model.Usuario;

@Repository
@Transactional
public class TarefaDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	private final static String QUERY_JOIN_TABELAS_PROJETO = "select t from Tarefa t join fetch t.iteracao i join fetch i.projeto pj join fetch pj.usuarios up ";
	
	public void salvar(Tarefa tarefa,List<Usuario> participantes){
		manager.persist(tarefa);
		tarefa.setParticipantes(participantes);
		this.alterar(tarefa);
	}
	
	public void deletar(Tarefa tarefa){
		tarefa = manager.find(Tarefa.class, tarefa.getId());
		manager.remove(tarefa);
	}
	
	public void alterar(Tarefa tarefa) {
		manager.merge(tarefa);
	}
	
	public Tarefa buscarTarefaPorId(Tarefa tarefa){
		return manager.find(Tarefa.class, tarefa.getId());
	}

	public List<Tarefa> buscarTarefasDoProjeto(Projeto projeto){
		return manager.createQuery("from Tarefa where projetoId = :projetoId ", Tarefa.class)
					  .setParameter("projetoId", projeto.getId())
					  .getResultList();
	}

	public Tarefa buscarTarefaTelaGeral(Long id, Usuario usuario, int criterio) {
		Tarefa tarefa;
		StringBuilder query = new StringBuilder();
		getBuscaTelaQuery(query);
		
		if(criterio == Constantes.EDITAR){
			List<Funcao> funcoes = Arrays.asList(Constantes.PERMISSOES_EDITAR_SCRUM);
			query.append("and up.funcao in :funcoes");
			tarefa = getParametersGeralQueryTela(id, usuario, query)
					.setParameter("funcoes", funcoes)
					.getSingleResult();
		}else{
			tarefa = getParametersGeralQueryTela(id, usuario, query)
					.getSingleResult();
		}
		
		Hibernate.initialize(tarefa.getParticipantes());
		return tarefa;
	}
	
	private TypedQuery<Tarefa> getParametersGeralQueryTela(Long id, Usuario usuario, StringBuilder query) {
		return manager.createQuery(query.toString(), Tarefa.class)
				.setParameter("id", id)
				.setParameter("banido", Funcao.BANIDO)
				.setParameter("usuario", usuario);
	}
	
	private void getBuscaTelaQuery(StringBuilder query) {
		query.append(QUERY_JOIN_TABELAS_PROJETO);
		query.append("where t.id = :id and up.pk.usuario = :usuario ");
		query.append("and up.funcao <> :banido ");
	}

	public List<Tarefa> buscarTarefasBrowser(String busca,Usuario usuario, boolean apenasParticipantes, boolean apenasPendentes) {
		List<Tarefa> resultado;
		StringBuilder query = new StringBuilder();
		query.append(QUERY_JOIN_TABELAS_PROJETO);
		if(apenasParticipantes){
			query.append("join t.participantes par ");
		}
		query.append("where ( pj.titulo like :busca or t.titulo like :busca ) ");
		query.append("and up.pk.usuario = :usuario ");
		
		if(apenasParticipantes){
			query.append("and :usuario in par ");
		}
		
		if(apenasPendentes){
			query.append("and t.etapa <> :etapa");
			resultado = manager.createQuery(query.toString(),Tarefa.class)
					.setParameter("busca", '%'+busca+'%')
					.setParameter("usuario", usuario)
					.setParameter("etapa", EtapaTarefa.FINALIZADA)
					.getResultList();
		}else{
			resultado = manager.createQuery(query.toString(),Tarefa.class)
					.setParameter("busca", '%'+busca+'%')
					.setParameter("usuario", usuario)
					.getResultList();
		}
		
		
		for(Tarefa tarefa : resultado){
			Hibernate.initialize(tarefa.getParticipantes());
		}
		
		return resultado;
	}

}
