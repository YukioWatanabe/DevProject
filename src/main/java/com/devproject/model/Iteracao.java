package com.devproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="iteracao")
public class Iteracao extends Persistivel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Size(max = 100)
	@Column(length = 100)
	@NotEmpty(message="O titulo da iteração é necessário.")
	private String titulo;
	
	@Size(max = 500)
	@Column(length = 500)
	private String descricao;
	
	@ManyToOne
	@NotNull
	private Projeto projeto;
	
	@OneToMany(mappedBy="iteracao" ,fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Tarefa> tarefas = new ArrayList<Tarefa>();
	
	@Temporal(TemporalType.DATE)
	@NotNull(message="A data de criação não é uma data válida.")
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	@Column
	private Boolean controleAutomatico;
	
	@Column
	private Boolean finalizada;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Boolean getControleAutomatico() {
		return controleAutomatico;
	}

	public void setControleAutomatico(Boolean controleAutomatico) {
		this.controleAutomatico = controleAutomatico;
	}

	public Boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
}