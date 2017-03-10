package com.devproject.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devproject.model.Funcao;
import com.devproject.model.SolicitacaoAmizade;
import com.devproject.model.SolicitacaoParticipante;
import com.devproject.model.SolicitacaoProjeto;
import com.devproject.model.Usuario;

@Repository
@Transactional
public class SolicitacaoDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;

	public SolicitacaoProjeto salvarSolicitacaoProjeto(SolicitacaoProjeto solicitacao){
		manager.persist(solicitacao);
		return solicitacao;
	}

	public SolicitacaoProjeto atualizarSolicitacaoProjeto(SolicitacaoProjeto solicitacao) {
		manager.merge(solicitacao);
		return solicitacao;
	}

	public void deletarSolicitacaoProjeto(SolicitacaoProjeto solicitacao, List<SolicitacaoProjeto> solicitacoesProjeto) {
		manager.remove(manager.getReference(SolicitacaoProjeto.class, solicitacao.getPk()));
		solicitacoesProjeto.remove(solicitacao);
	}

	public SolicitacaoAmizade salvarSolicitacaoAmizade(SolicitacaoAmizade solicitacao) {
		manager.persist(solicitacao);
		return solicitacao;
	}

	public SolicitacaoAmizade atualizarSolicitacaoAmizade(SolicitacaoAmizade solicitacao) {
		manager.merge(solicitacao);
		return solicitacao;
	}

	public void deletarSolicitacaoAmizade(SolicitacaoAmizade solicitacao, List<SolicitacaoAmizade> solicitacoesAmizade) {
		manager.remove(manager.getReference(SolicitacaoAmizade.class, solicitacao.getPk()));
		solicitacoesAmizade.remove(solicitacao);
	}
	
	public SolicitacaoProjeto buscaSolicitacaoProjetoPorId(SolicitacaoProjeto solicitacao) {
		return manager.find(SolicitacaoProjeto.class, solicitacao.getPk());
	}
	
	public SolicitacaoAmizade buscaSolicitacaoProjetoPorId(SolicitacaoAmizade solicitacao) {
		return manager.find(SolicitacaoAmizade.class, solicitacao.getPk());
	}

	public List<SolicitacaoProjeto> buscaSolicitacoesProjeto(Usuario usuario) {
		StringBuilder query = new StringBuilder();
		List<Funcao> funcoes = new ArrayList<Funcao>();
		funcoes.add(Funcao.ADMINISTRADOR);
		funcoes.add(Funcao.CRIADOR);
		
		query.append("select s from SolicitacaoProjeto s join fetch s.pk.solicitante sol join fetch s.pk.receptor rec ");
		query.append("join rec.usuarios up join up.pk.usuario usu ");
		query.append("where usu = :usuario and up.funcao in (:funcoes)");
		
		return manager.createQuery(query.toString(), SolicitacaoProjeto.class)
					  .setParameter("usuario", usuario)
					  .setParameter("funcoes", funcoes)
					  .getResultList();
	}

	public List<SolicitacaoAmizade> buscaSolicitacoesAmizade(Usuario usuario) {
		StringBuilder query = new StringBuilder();
		
		query.append("select s from SolicitacaoAmizade s join fetch s.pk.solicitante sol join fetch s.pk.receptor rec ");
		query.append("where rec = :usuario");
		
		return manager.createQuery(query.toString(), SolicitacaoAmizade.class)
					  .setParameter("usuario", usuario)
					  .getResultList();
	}

	public SolicitacaoParticipante salvarSolicitacaoParticipante(SolicitacaoParticipante solicitacao) {
		manager.persist(solicitacao);
		return solicitacao;
	}

	public SolicitacaoParticipante atualizarSolicitacaoParticipante(SolicitacaoParticipante solicitacao) {
		manager.merge(solicitacao);
		return solicitacao;
	}

	public void deletarSolicitacaoParticipante(SolicitacaoParticipante solicitacao, List<SolicitacaoParticipante> solicitacoesParticipante) {
		manager.remove(manager.getReference(SolicitacaoParticipante.class, solicitacao.getPk()));
		solicitacoesParticipante.remove(solicitacao);
	}

	public List<SolicitacaoParticipante> buscaSolicitacoesParticipacao(Usuario usuario) {
		StringBuilder query = new StringBuilder();
				
		query.append("select s from SolicitacaoParticipante s join fetch s.pk.solicitante sol join fetch s.pk.receptor rec ");
		query.append("where rec = :usuario");
		
		return manager.createQuery(query.toString(), SolicitacaoParticipante.class)
					  .setParameter("usuario", usuario)
					  .getResultList();
	}

	public SolicitacaoParticipante buscaSolicitacaoParticipantePorId(SolicitacaoParticipante solicitacao) {
		return manager.find(SolicitacaoParticipante.class, solicitacao.getPk());
	}

}