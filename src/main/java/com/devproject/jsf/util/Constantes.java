package com.devproject.jsf.util;

import com.devproject.model.Funcao;

public class Constantes {
	
	public static final int VISUALIZAR = 0;
	public static final int EDITAR = 1;	
	public static final Funcao[] PERMISSOES_EDITAR_SCRUM = {Funcao.ADMINISTRADOR, Funcao.CRIADOR, Funcao.SCRUM_MASTER};
	public static final Funcao[] FUNCOES_SOLICITAVEIS = {Funcao.ADMINISTRADOR, Funcao.SCRUM_MASTER,Funcao.ACOMPANHANTE, Funcao.MEMBRO, Funcao.FINANCIADOR};
}
