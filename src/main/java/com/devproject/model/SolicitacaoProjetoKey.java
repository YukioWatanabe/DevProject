package com.devproject.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class SolicitacaoProjetoKey implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Usuario solicitante;
	
	@ManyToOne
	private Projeto receptor;
	
	public SolicitacaoProjetoKey() {
		
	}
	
	public SolicitacaoProjetoKey(Usuario solicitante, Projeto receptor) {
		this.solicitante = solicitante;
		this.receptor = receptor;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}

	public Projeto getReceptor() {
		return receptor;
	}

	public void setReceptor(Projeto receptor) {
		this.receptor = receptor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((receptor == null) ? 0 : receptor.hashCode());
		result = prime * result + ((solicitante == null) ? 0 : solicitante.hashCode());
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
		SolicitacaoProjetoKey other = (SolicitacaoProjetoKey) obj;
		if (receptor == null) {
			if (other.receptor != null)
				return false;
		} else if (!receptor.equals(other.receptor))
			return false;
		if (solicitante == null) {
			if (other.solicitante != null)
				return false;
		} else if (!solicitante.equals(other.solicitante))
			return false;
		return true;
	}

}
