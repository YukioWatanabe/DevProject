package com.devproject.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devproject.dao.SolicitacaoDao;
import com.devproject.model.Projeto;
import com.devproject.model.SolicitacaoAmizade;
import com.devproject.model.SolicitacaoAmizadeKey;
import com.devproject.model.SolicitacaoParticipante;
import com.devproject.model.SolicitacaoParticipanteKey;
import com.devproject.model.SolicitacaoProjeto;
import com.devproject.model.SolicitacaoProjetoKey;
import com.devproject.model.Usuario;

@Service
public class SolicitacaoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SolicitacaoDao dao;
	
	public SolicitacaoProjeto salvarSolicitacaoProjeto(SolicitacaoProjeto solicitacao){
		return dao.salvarSolicitacaoProjeto(solicitacao);
	}
	
	public SolicitacaoProjeto atualizarSolicitacaoProjeto(SolicitacaoProjeto solicitacao){
		return dao.atualizarSolicitacaoProjeto(solicitacao);
	}
	
	public SolicitacaoProjeto deletarSolicitacaoProjeto(Usuario solicitante, Projeto receptor, List<SolicitacaoProjeto> solicitacoesProjeto){
		SolicitacaoProjeto solicitacao = new SolicitacaoProjeto();
		solicitacao.setPk(new SolicitacaoProjetoKey(solicitante,receptor));
		
		dao.deletarSolicitacaoProjeto(solicitacao, solicitacoesProjeto);
		
		return solicitacao;
	}
	
	public SolicitacaoAmizade salvarSolicitacaoAmizade(SolicitacaoAmizade solicitacao){
		return dao.salvarSolicitacaoAmizade(solicitacao);
	}
	
	public SolicitacaoAmizade atualizarSolicitacaoAmizade(SolicitacaoAmizade solicitacao){
		return dao.atualizarSolicitacaoAmizade(solicitacao);
	}
	
	public SolicitacaoAmizade deletarSolicitacaoAmizade(Usuario solicitante, Usuario receptor, List<SolicitacaoAmizade> solicitacoesAmizade){
		SolicitacaoAmizade solicitacao = new SolicitacaoAmizade();
		solicitacao.setPk(new SolicitacaoAmizadeKey(solicitante, receptor));
		
		dao.deletarSolicitacaoAmizade(solicitacao, solicitacoesAmizade);
		
		return solicitacao;
	}

	public List<SolicitacaoProjeto> buscaSolicitacoesProjeto(Usuario usuario) {
		return dao.buscaSolicitacoesProjeto(usuario);
	}

	public List<SolicitacaoAmizade> buscaSolicitacoesAmizade(Usuario usuario) {
		return dao.buscaSolicitacoesAmizade(usuario);
	}

	public SolicitacaoProjeto buscaSolicitacaoProjetoPorId(Usuario solicitante, Projeto receptor) {
		SolicitacaoProjeto solicitacao = new SolicitacaoProjeto();
		
		solicitacao.setPk(new SolicitacaoProjetoKey(solicitante, receptor));
		return dao.buscaSolicitacaoProjetoPorId(solicitacao);
	}

	public SolicitacaoAmizade buscaSolicitacaoAmizadePorId(Usuario solicitante, Usuario receptor) {
		SolicitacaoAmizade solicitacao = new SolicitacaoAmizade();
		
		solicitacao.setPk(new SolicitacaoAmizadeKey(solicitante, receptor));
		return dao.buscaSolicitacaoProjetoPorId(solicitacao);
	}
	
	public SolicitacaoParticipante salvarSolicitacaoParticipante(SolicitacaoParticipante solicitacao){
		return dao.salvarSolicitacaoParticipante(solicitacao);
	}
	
	public SolicitacaoParticipante atualizarSolicitacaoParticipante(SolicitacaoParticipante solicitacao){
		return dao.atualizarSolicitacaoParticipante(solicitacao);
	}
	
	public SolicitacaoParticipante deletarSolicitacaoParticipante(Projeto solicitante, Usuario receptor, List<SolicitacaoParticipante> solicitacoesParticipante){
		SolicitacaoParticipante solicitacao = new SolicitacaoParticipante();
		solicitacao.setPk(new SolicitacaoParticipanteKey(solicitante, receptor));
		
		dao.deletarSolicitacaoParticipante(solicitacao, solicitacoesParticipante);
		
		return solicitacao;
	}

	public List<SolicitacaoParticipante> buscaSolicitacoesParticipacao(Usuario usuario) {
		return dao.buscaSolicitacoesParticipacao(usuario);
	}

	public SolicitacaoParticipante buscaSolicitacaoParticipantePorId(Projeto solicitante, Usuario receptor) {
		SolicitacaoParticipante solicitacao = new SolicitacaoParticipante();
		
		solicitacao.setPk(new SolicitacaoParticipanteKey(solicitante, receptor));
		return dao.buscaSolicitacaoParticipantePorId(solicitacao);
	}

}