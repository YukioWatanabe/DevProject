package com.devproject.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="usuario_projeto")
public class UsuarioProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioProjetoKey pk;
	
	@Column
	@NotNull
	@Enumerated(EnumType.STRING)
	private Funcao funcao;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date dataInscricao;
	
	@Temporal(TemporalType.DATE)
	private Date dataSaida;

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao relacionamento) {
		this.funcao = relacionamento;
	}

	public UsuarioProjetoKey getPk() {
		return pk;
	}

	public void setPk(UsuarioProjetoKey pk) {
		this.pk = pk;
	}

	public Date getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioProjeto other = (UsuarioProjeto) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
  
}