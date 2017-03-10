package com.devproject.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devproject.jsf.util.Constantes;
import com.devproject.model.Funcao;
import com.devproject.model.Iteracao;
import com.devproject.model.Usuario;

@Repository
@Transactional
public class IteracaoDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;
	
	public Iteracao salvar(@Valid Iteracao iteracao){
		manager.persist(iteracao);
		return iteracao;
	}
	
	public Iteracao alterar(@Valid Iteracao iteracao) {
		manager.merge(iteracao);
		return iteracao;
	}
	
	public void deletar(Iteracao iteracao){
		manager.remove(iteracao);
	}
	
	public Iteracao buscarIteracaoPorId(Iteracao iteracao){
		return manager.find(Iteracao.class, iteracao.getId());
	}

	public Iteracao buscarIteracaoTelaGeral(Long id, Usuario usuario, int criterio) {
		Iteracao iteracao;
		StringBuilder query = new StringBuilder();
		getBuscaTelaQuery(query);
		
		if(Constantes.EDITAR == criterio){
			List<Funcao> funcoes = Arrays.asList(Constantes.PERMISSOES_EDITAR_SCRUM);
			query.append("and up.funcao in :funcoes");
			iteracao = getParametersGeralQueryTela(id, usuario, query)
					.setParameter("funcoes", funcoes)
					.getSingleResult();
		}else{
			iteracao = getParametersGeralQueryTela(id, usuario, query)
					.getSingleResult();
		}
		
		Hibernate.initialize(iteracao.getTarefas());
		Hibernate.initialize(iteracao.getProjeto().getUsuarios());
		return iteracao;
	}
	
	private TypedQuery<Iteracao> getParametersGeralQueryTela(Long id, Usuario usuario, StringBuilder query) {
		return manager.createQuery(query.toString(), Iteracao.class)
				.setParameter("id", id)
				.setParameter("banido", Funcao.BANIDO)
				.setParameter("usuario", usuario);
	}
	
	private void getBuscaTelaQuery(StringBuilder query) {
		query.append("select i from Iteracao i join fetch i.projeto p join fetch p.usuarios up ");
		query.append("where i.id = :id and up.pk.usuario = :usuario ");
		query.append("and up.funcao <> :banido ");
	}
}
