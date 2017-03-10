package com.devproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="tarefa")
public class Tarefa extends Persistivel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Size(max=100)
	@Column(length=100)
	@NotEmpty(message="É necessário informar o título da tarefa.")
	private String titulo;
	
	@Size(max=500)
	@Column(length=500)
	private String descricaoTarefa;
	
	@Size(max=500)
	@Column(length=500)
	private String descricaoProblema;
	
	@ManyToOne
	@NotNull
	private Iteracao iteracao;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message="É necessário informar o etapa da tarefa.")
	private EtapaTarefa etapa;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Usuario> participantes = new ArrayList<Usuario>(0);
	
	@Temporal(TemporalType.DATE)
	@NotNull(message="É necessário informar a data de inicio da tarefa.")
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	@NotNull(message="É necessário informar a data de fim da tarefa.")
	private Date prazoTermino;
	
	@Temporal(TemporalType.TIME)
	private Date tempoEstimado;
	
	@Column(insertable=false)
	@Temporal(TemporalType.TIME)
	private Date tempoFeito;
	
	@Column
	private Boolean finalizada;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricaoTarefa() {
		return descricaoTarefa;
	}

	public void setDescricaoTarefa(String descricaoTarefa) {
		this.descricaoTarefa = descricaoTarefa;
	}
	public Iteracao getIteracao() {
		return iteracao;
	}

	public void setIteracao(Iteracao iteracao) {
		this.iteracao = iteracao;
	}

	public EtapaTarefa getEtapa() {
		return etapa;
	}
	public void setEtapa(EtapaTarefa etapa) {
		this.etapa = etapa;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getPrazoTermino() {
		return prazoTermino;
	}

	public void setPrazoTermino(Date prazoTermino) {
		this.prazoTermino = prazoTermino;
	}

	public Date getTempoEstimado() {
		return tempoEstimado;
	}

	public void setTempoEstimado(Date tempoEstimado) {
		this.tempoEstimado = tempoEstimado;
	}
	public Date getTempoFeito() {
		return tempoFeito;
	}

	public void setTempoFeito(Date tempoFeito) {
		this.tempoFeito = tempoFeito;
	}

	public String getDescricaoProblema() {
		return descricaoProblema;
	}

	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}

	public Boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}

	public List<Usuario> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Usuario> participantes) {
		this.participantes = participantes;
	}
}