package com.devproject.model;

import java.io.Serializable;

public enum Funcao implements Serializable{
	MEMBRO("Membro"),
	FINANCIADOR("Financiador"),
	ADMINISTRADOR("Administrador"),
	CRIADOR("Criador"),
	SCRUM_MASTER("Scrum Master"),
	ACOMPANHANTE("Acompanhante"),
	BANIDO("Banido");
	
	private final String descricao;
	
	private Funcao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public static Funcao getPorCodigo(int codigo){
		Funcao resultado = null;
		for(Funcao funcao : values()){
			if(funcao.ordinal() == codigo){
				resultado = funcao;
				break;
			}
		}
		return resultado;
	}
	
	public static Funcao getPorDescricao(String descricao){
		Funcao resultado = null;
		for(Funcao funcao : values()){
			if(funcao.getDescricao().equals(descricao)){
				resultado = funcao;
				break;
			}
		}
		return resultado;
	}
}
