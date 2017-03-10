package com.devproject.controler;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.devproject.jsf.util.FacesUtil;

@ManagedBean(name="index")
@RequestScoped
public class IndexBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public void inicializar(){
		Map<String, String> attributes = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(attributes != null && "true".equals(attributes.get("sucesso"))){
			FacesUtil.adicionaMensagemInfo("Cadastrado com sucesso", "Bem vindo ao DevProject, "+attributes.get("usuario")+"!");
		}
		if(attributes != null && "true".equals(attributes.get("logout"))){
			FacesUtil.adicionaMensagemInfo("Logout efetuado com sucesso!", "Obrigado por utilizar o DevProject. Aguardamos o seu retorno.");
		}
	}
}
