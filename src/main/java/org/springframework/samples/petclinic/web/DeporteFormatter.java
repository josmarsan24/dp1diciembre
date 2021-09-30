package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Component;

@Component
public class DeporteFormatter implements Formatter<Deporte>{
	
	private final AthleteService athleteService;
	
	@Autowired
	public DeporteFormatter(AthleteService athleteService) {
		this.athleteService = athleteService;
	}
	
	@Override
	public String print(Deporte deporte, Locale locale) {
		return deporte.getName();
	}

	@Override
	public Deporte parse(String text, Locale locale) throws ParseException {
		Collection<Deporte> deportes = this.athleteService.findDeporteTypes();
		for (Deporte d:deportes) {
			if (d.getName().equals(text)) {
				return d;
			}
		}
		throw new ParseException("deporte no encontrado: " + text, 0);
	}

}
