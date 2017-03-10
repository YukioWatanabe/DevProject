package com.devproject.model;

import java.io.Serializable;

public enum TipoSolicitacao implements Serializable {
	
	AMIZADE("Amizade"),
	PROJETO("Participação no Projeto"),
	FINANCIAMENTO("Financiamento");
	
	private String tipo;
	
	private TipoSolicitacao(String tipo){
		this.tipo = tipo;
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
	public static TipoSolicitacao getUsuarioProjetoEnumPorCodigo(int codigo){
		TipoSolicitacao resultado = null;
		for(TipoSolicitacao tipo : values()){
			if(tipo.ordinal() == codigo){
				resultado = tipo;
				break;
			}
		}
		return resultado;
	}
	
	public static TipoSolicitacao getFuncaoPorDescricao(String descricao){
		TipoSolicitacao resultado = null;
		for(TipoSolicitacao tipo : values()){
			if(tipo.getTipo().equals(descricao)){
				resultado = tipo;
				break;
			}
		}
		return resultado;
	}
}
