package com.devproject.service;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devproject.dao.IteracaoDao;
import com.devproject.jsf.util.Constantes;
import com.devproject.model.Iteracao;
import com.devproject.model.Usuario;

@Service
public class IteracaoService implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Autowired
	private IteracaoDao dao;
	
	public Iteracao salvar(Iteracao iteracao){
		return dao.salvar(iteracao);
	}
	
	public void deletar(Iteracao iteracao){
		dao.deletar(iteracao);
	}
	
	public Iteracao alterar(Iteracao iteracao){
		return dao.alterar(iteracao);
	}
	
	public Iteracao buscarIteracaoPorId(Iteracao iteracao){
		return dao.buscarIteracaoPorId(iteracao);
	}

	public Iteracao buscarIteracaoTela(Long id, Usuario usuario) {
		return buscarIteracaoTelaGeral(id, usuario, Constantes.VISUALIZAR);
	}

	public Iteracao buscarIteracaoTelaEdit(Long id, Usuario usuario) {
		return buscarIteracaoTelaGeral(id, usuario, Constantes.EDITAR);
	}
	
	public Iteracao buscarIteracaoTelaGeral(Long id, Usuario usuario, int criterio) {
		Iteracao iteracao = dao.buscarIteracaoTelaGeral(id, usuario, criterio);
		if(iteracao != null){
			return iteracao;
		}else{
			throw new NoResultException();
		}
	}
}
