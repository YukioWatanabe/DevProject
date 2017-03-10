package com.devproject.controler;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.devproject.jsf.util.FacesUtil;
import com.devproject.security.UsuarioSistema;

@ManagedBean(name="login")
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String username;
	
	private UsuarioSistema usuario;
	
	public void logar() throws ServletException, IOException{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((HttpServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_check");

        dispatcher.forward((HttpServletRequest) context.getRequest(),
                (HttpServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)
        		FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        
        if(auth != null){
        	usuario = (UsuarioSistema)auth.getPrincipal();
        }
	}
	
	public void logout() throws ServletException, IOException{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((HttpServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_logout");

        dispatcher.forward((HttpServletRequest) context.getRequest(),
                (HttpServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void checkError(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if("true".equals(request.getParameter("invalid"))){
			FacesUtil.adicionaMensagemErro("Usuario ou Senha inválidos.", "O usuario ou senha informados são inválidos.");
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UsuarioSistema getUsuario() {
		return usuario;
	}

}
