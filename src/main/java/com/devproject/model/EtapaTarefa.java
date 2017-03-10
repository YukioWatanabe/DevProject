package com.devproject.model;

public enum EtapaTarefa{
	NAO_REALIZADA("Não realizada"),
	EM_DESENVOLVIMENTO("Em desenvolvimento"),
	EM_ANDAMENTO("Em andamento"),
	BLOQUEADA("Bloqueada"),
	FINALIZADA("Finalizada"),
	CANCELADA("Cancelada");
	
	private String descricao;
	
	private EtapaTarefa(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public static EtapaTarefa gePorCodigo(int codigo){
		EtapaTarefa resultado = null;
		for(EtapaTarefa etapa : values()){
			if(etapa.ordinal() == codigo){
				resultado = etapa;
				break;
			}
		}
		return resultado;
	}
	
	public static EtapaTarefa getPorDescricao(String descricao){
		EtapaTarefa resultado = null;
		for(EtapaTarefa etapa : values()){
			if(etapa.getDescricao().equals(descricao)){
				resultado = etapa;
				break;
			}
		}
		return resultado;
	}

	
}
