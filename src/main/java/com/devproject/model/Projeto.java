package com.devproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.MapKey;
import javax.persistence.MapKeyClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="projeto")
public class Projeto extends Persistivel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Size(max=100)
	@Column(length = 100)
	@NotEmpty(message="O titulo não pode estar em branco.")
	private String titulo;

	@Column
	@NotNull(message="É necessário informar o tipo do projeto.")
	@Enumerated(EnumType.STRING)
	private TipoProjeto tipo;
	
	@Lob
	private byte[] logo;

	@Column
	@NotNull(message="A data de criação não é uma data válida.")
	@Temporal(TemporalType.DATE)
	private Date dataCriacao;

	@Column
	@Temporal(TemporalType.DATE)
	private Date prazo;

	@Size(max=2000)
	@Column(length=2000)
	private String descricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.projeto", cascade = CascadeType.ALL)
	@MapKeyClass(UsuarioProjetoKey.class)
	@MapKey(name="pk.projeto")
	private Map<UsuarioProjetoKey,UsuarioProjeto> usuarios = new HashMap<UsuarioProjetoKey,UsuarioProjeto>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projeto", cascade = CascadeType.ALL)
	private List<Iteracao> iteracoes = new ArrayList<Iteracao>(0);
	
	@ElementCollection(targetClass=Habilidade.class, fetch=FetchType.LAZY)
	@Enumerated(EnumType.STRING)
	private List<Habilidade> requisitos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.receptor", cascade = CascadeType.ALL)
	@MapKeyClass(SolicitacaoProjetoKey.class)
	@MapKey(name="pk.receptor")
	private Map<SolicitacaoProjetoKey,SolicitacaoProjeto> solicitacoesProjetoRecebidas = new HashMap<SolicitacaoProjetoKey,SolicitacaoProjeto>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.solicitante", cascade = CascadeType.ALL)
	@MapKeyClass(SolicitacaoParticipanteKey.class)
	@MapKey(name="pk.solicitante")
	private Map<SolicitacaoParticipanteKey,SolicitacaoParticipante> solicitacoesProjetoEnviadas = new HashMap<SolicitacaoParticipanteKey,SolicitacaoParticipante>(0);
	
	@Column
	private Boolean finalizado;
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public TipoProjeto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProjeto tipo) {
		this.tipo = tipo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getPrazo() {
		return prazo;
	}

	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Map<UsuarioProjetoKey,UsuarioProjeto> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(HashMap<UsuarioProjetoKey,UsuarioProjeto> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void setUsuarios(Map<UsuarioProjetoKey, UsuarioProjeto> usuarios) {
		this.usuarios = usuarios;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public List<Habilidade> getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(List<Habilidade> requisitos) {
		this.requisitos = requisitos;
	}

	public List<Iteracao> getIteracoes() {
		return iteracoes;
	}

	public void setIteracoes(List<Iteracao> iteracoes) {
		this.iteracoes = iteracoes;
	}

	public Map<SolicitacaoProjetoKey, SolicitacaoProjeto> getSolicitacoesProjetoRecebidas() {
		return solicitacoesProjetoRecebidas;
	}

	public void setSolicitacoesProjetoRecebidas(
			Map<SolicitacaoProjetoKey, SolicitacaoProjeto> solicitacoesProjetoRecebidas) {
		this.solicitacoesProjetoRecebidas = solicitacoesProjetoRecebidas;
	}

	public Map<SolicitacaoParticipanteKey, SolicitacaoParticipante> getSolicitacoesProjetoEnviadas() {
		return solicitacoesProjetoEnviadas;
	}

	public void setSolicitacoesProjetoEnviadas(
			Map<SolicitacaoParticipanteKey, SolicitacaoParticipante> solicitacoesProjetoEnviadas) {
		this.solicitacoesProjetoEnviadas = solicitacoesProjetoEnviadas;
	}

	public Boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}

}
