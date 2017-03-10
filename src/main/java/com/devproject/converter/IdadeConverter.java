package com.devproject.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("idade")
public class IdadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date data;
		String idade = null;
		try {
			data = value instanceof java.sql.Date? df.parse(value.toString()):(Date)value;
			idade = getIdade(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return idade;
	}

	private String getIdade(Date dataRecebida) {
		Date dataAtual = new Date();
		Calendar calendarAtual = getCalendar(dataAtual);
		Calendar calendarRecebido;
		
		int idadeNumero;
		
		calendarRecebido = getCalendar(dataRecebida);
		idadeNumero = calendarAtual.get(Calendar.YEAR) - calendarRecebido.get(Calendar.YEAR);
		
		if ((calendarAtual.get(Calendar.DAY_OF_YEAR) < calendarRecebido.get(Calendar.DAY_OF_YEAR)) && idadeNumero != 0) {
	        idadeNumero--;
	    }
		
		return String.valueOf(idadeNumero);
	}

	
	private Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance(Locale.getDefault());
	    cal.setTime(date);
	    return cal;
	}
}
