package com.devproject.model;

import java.io.Serializable;

public enum TipoProjeto implements Serializable{

	NEGOCIOS("Negócios"), 
	ACADEMICO("Acadêmico"),
	OUTROS("Outros");
	
	private String descricao;

	TipoProjeto(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoProjeto getPorCodigo(int codigo){
		TipoProjeto resultado = null;
		for(TipoProjeto tipoProjeto : values()){
			if(tipoProjeto.ordinal() == codigo){
				resultado = tipoProjeto;
				break;
			}
		}
		return resultado;
	}
	
	public static TipoProjeto getPorDescricao(String descricao){
		TipoProjeto resultado = null;
		for(TipoProjeto tipoProjeto : values()){
			if(tipoProjeto.getDescricao().equals(descricao)){
				resultado = tipoProjeto;
				break;
			}
		}
		return resultado;
	}

}
