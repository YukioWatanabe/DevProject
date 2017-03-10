package com.devproject.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.devproject.model.TipoProjeto;

@FacesConverter("tipoprojeto")
public class TipoProjetoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return TipoProjeto.getPorDescricao(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			TipoProjeto retorno = (TipoProjeto) value;
			return retorno.getDescricao();
		}else{
			return null;
		}
	}

}