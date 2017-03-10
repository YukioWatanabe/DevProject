package com.devproject.jsf.util;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class FacesUtil {
	
	public static void adicionaMensagemErro(String titulo, String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensagem));
	}
	
	public static void adicionaMensagemInfo(String titulo, String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensagem));
	}
	
	public static void adicionaMensagemAviso(String titulo, String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensagem));
	}
	
	public static void redirect(String page){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		String contextPath = externalContext.getRequestContextPath();
		
		try {
			externalContext.redirect(contextPath + page);
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}
		
	}
}
