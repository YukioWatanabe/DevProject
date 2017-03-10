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
@Table(name = "solicitacao_participante")
public class SolicitacaoParticipante implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SolicitacaoParticipanteKey pk;
	
	@Size(max=250)
	@Column(length = 250)
	private String mensagem;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date dataSolicitacao;
	
	private Funcao funcao;
	
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

	public SolicitacaoParticipanteKey getPk() {
		return pk;
	}

	public void setPk(SolicitacaoParticipanteKey pk) {
		this.pk = pk;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
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
		SolicitacaoParticipante other = (SolicitacaoParticipante) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
}