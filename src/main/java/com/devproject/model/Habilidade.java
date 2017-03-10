package com.devproject.model;

import java.io.Serializable;

public enum Habilidade implements Serializable {
	GESTAO_PROJETO(1,"Gestão de Projeto"),
	UML(2,"UML"),
	SCRUM(3,"Scrum"),
	PHP(4,"PHP"),
	JAVA(5,"Java"),
	RUBY(6,"Ruby"),
	C(7,"C"),
	C_PLUS_PLUS(8,"C++"),
	C_SHARP(9,"C#"),
	PHYTON(10,"Phyton"),
	DELPHI(11,"Delphi"),
	COBOL(12,"Cobol"),
	ASSEMBLY(13,"Assembly"),
	HTML(14,"HTML"),
	JAVASCRIPT(15,"Javascript"),
	CSS(16,"CSS"),
	JQUERY(17,"JQuery"),
	ANGULAR_JS(18,"Angular JS"),
	SQL(19,"SQL"),
	JAVA_EE(20,"Java EE"),
	STRUTS(21,"Struts"),
	STRUTS2(22,"Struts 2"),
	JSF(23,"JSF"),
	HIBERNATE(24,"Hibernate"),
	DOT_NET(25,".Net"),
	ASP_NET(26,"Asp.Net"),
	ASP(27,"Asp");
	
	private final int codigo;
	private final String valor;
	
	private Habilidade(int codigo, String valor){
		this.codigo = codigo;
		this.valor = valor;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public String getValor() {
		return valor;
	}
	
	public static Habilidade getHabilidadePorCodigo(int codigo){
		Habilidade resultado = null;
		for(Habilidade habilidade : Habilidade.values()){
			if(habilidade.getCodigo() == codigo){
				resultado = habilidade;
				break;
			}
		}
		return resultado;
	}
	
	public static Habilidade getHabilidadePorValor(String valor){
		Habilidade resultado = null;
		for(Habilidade habilidade : Habilidade.values()){
			if(habilidade.getValor().equals(valor)){
				resultado = habilidade;
				break;
			}
		}
		return resultado;
	}
}
