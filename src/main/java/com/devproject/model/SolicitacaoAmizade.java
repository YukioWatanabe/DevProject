package com.devproject.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "solicitacao_amizade")
public class SolicitacaoAmizade implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SolicitacaoAmizadeKey pk;
	
	@Size(max=250)
	@Column(length = 250)
	private String mensagem;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date dataSolicitacao;
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public SolicitacaoAmizadeKey getPk() {
		return pk;
	}

	public void setPk(SolicitacaoAmizadeKey pk) {
		this.pk = pk;
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
		SolicitacaoAmizade other = (SolicitacaoAmizade) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
}