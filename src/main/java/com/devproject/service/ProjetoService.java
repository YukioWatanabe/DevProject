package com.devproject.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devproject.dao.ProjetoDao;
import com.devproject.model.Habilidade;
import com.devproject.model.Iteracao;
import com.devproject.model.Projeto;
import com.devproject.model.Usuario;

@Service
public class ProjetoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProjetoDao dao;
	
	public Projeto salvar(Projeto projeto){
		return dao.salvar(projeto);
	}
	
	public void deletar(Projeto projeto){
		dao.deletar(projeto);
	}
	
	public Projeto atualizar(Projeto projeto){
		return dao.atualizar(projeto);
	}
	
	public Projeto buscarProjetoPorId(Long id){
		return dao.buscarProjetoPorId(id);
	}

	public List<Projeto> buscarProjetosRecomendados(Usuario usuario) {
		return dao.buscarProjetosRecomendados(usuario);
	}

	public List<Projeto> buscarMeusProjetos(Usuario usuario) {
		return dao.buscarMeusProjetos(usuario);
	}

	public List<Projeto> buscarProjetosParticipantes(Usuario usuario) {
		return dao.buscarProjetosParticipantes(usuario);
	}

	public List<Projeto> buscarProjetosBrowser(String busca, List<Habilidade> habilidadesSelecionadas) {
		return dao.buscarProjetosBrowser(busca, habilidadesSelecionadas);
	}

	public Projeto deletarIteracao(Projeto projeto, Iteracao iteracaoSelecionada) {
		return dao.deletarIteracao(projeto,iteracaoSelecionada);
	}

}