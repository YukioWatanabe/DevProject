package com.devproject.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devproject.dao.TarefaDao;
import com.devproject.jsf.util.Constantes;
import com.devproject.model.Projeto;
import com.devproject.model.Tarefa;
import com.devproject.model.Usuario;

@Service
public class TarefaService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TarefaDao dao;
	
	public void salvar(Tarefa tarefa, List<Usuario> participantes){
		dao.salvar(tarefa, participantes);
	}
	
	public void deletar(Tarefa tarefa){
		dao.deletar(tarefa);
	}
	
	public void alterar(Tarefa tarefa) {
		dao.alterar(tarefa);
	}
	
	public Tarefa buscarTarefaPorId(Tarefa tarefa){
		return dao.buscarTarefaPorId(tarefa);
	}

	public List<Tarefa> buscarTarefasDoProjeto(Projeto projeto){
		return dao.buscarTarefasDoProjeto(projeto);
	}

	public Tarefa buscarTarefaTela(Long id, Usuario usuario) {
		return buscarTarefaTelaGeral(id, usuario, Constantes.VISUALIZAR);
	}
	
	public Tarefa buscarTarefaTelaEdit(Long id, Usuario usuario) {
		return buscarTarefaTelaGeral(id, usuario, Constantes.EDITAR);
	}
	
	private Tarefa buscarTarefaTelaGeral(Long id, Usuario usuario, int criterio) {
		Tarefa tarefa = new Tarefa();
		tarefa = dao.buscarTarefaTelaGeral(id, usuario,criterio);
		if(tarefa != null){
			return tarefa;
		}else{
			throw new NoResultException();
		}
	}

	public List<Tarefa> buscarMinhasTarefas(Usuario usuario) {
		return this.buscarTarefasBrowser("",usuario,true,true);
	}

	public List<Tarefa> buscarTarefasBrowser(String busca, Usuario usuario, boolean apenasParticipantes, boolean apenasPendentes) {
		return dao.buscarTarefasBrowser(busca,usuario,apenasParticipantes,apenasPendentes);
	}

}
