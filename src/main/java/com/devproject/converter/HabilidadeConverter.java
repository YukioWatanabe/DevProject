package com.devproject.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.devproject.model.Habilidade;

@FacesConverter("habilidade")
public class HabilidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return Habilidade.getHabilidadePorValor(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Habilidade retorno = (Habilidade) value;
			return retorno.getValor();
		}else{
			return null;
		}
	}

}
