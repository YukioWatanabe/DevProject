package com.devproject.converter;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.devproject.model.Usuario;

@ManagedBean(name="participanteConverter")
@RequestScoped
public class ParticipanteConverter implements Converter, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{tarefa.participantes}")
	private ArrayList<Usuario> participantesProjeto;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		for(Usuario participante : participantesProjeto){
			if(participante.getId().equals(Long.parseLong(value))){
				return participante;
			}
		}
		return null;
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Usuario participante = (Usuario) value;
		return String.valueOf(participante.getId());
	}

	public ArrayList<Usuario> getParticipantesProjeto() {
		return participantesProjeto;
	}

	public void setParticipantesProjeto(ArrayList<Usuario> participantesProjeto) {
		this.participantesProjeto = participantesProjeto;
	}

}
