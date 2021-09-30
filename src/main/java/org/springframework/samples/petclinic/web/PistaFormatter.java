package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.service.TorneoService;
import org.springframework.stereotype.Component;

@Component
public class PistaFormatter implements Formatter<Pista>{

	private final TorneoService torneoService;
	
	public PistaFormatter(TorneoService torneoService) {
		this.torneoService = torneoService;
	}
	
	@Override
	public String print(Pista pista, Locale locale) {

		return pista.getName();
	}

	@Override
	public Pista parse(String text, Locale locale) throws ParseException {
		Collection<Pista> pistas = this.torneoService.findPistaTypes();
		for (Pista p: pistas) {
			if (p.getName().equals(text)) {
				return p;
			}
		}
		throw new ParseException("Pista no encontrada: " + text, 0);
	}

}
