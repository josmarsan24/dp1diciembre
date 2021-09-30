package org.springframework.samples.petclinic.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.service.TorneoService;

@ExtendWith(MockitoExtension.class)
public class PistaFormatterTest {

	@Mock
	private TorneoService torneoService;
	 
	private PistaFormatter pistaFormatter;
	
	@BeforeEach
	void setup() {
		pistaFormatter = new PistaFormatter(torneoService);
	}
	
	@Test
	void testPrint() {
		Pista pista = new Pista();
		pista.setName("Nombre");
		String pistaName = pistaFormatter.print(pista, Locale.ENGLISH);
		assertEquals("Nombre", pistaName);
	}
	
	@Test
	void shouldParse() throws ParseException {
		Mockito.when(torneoService.findPistaTypes()).thenReturn(makePistas());
		Pista p = pistaFormatter.parse("Estadio de Valencia", Locale.ENGLISH);
		assertEquals("Estadio de Valencia", p.getName());
	}
	
	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(torneoService.findPistaTypes()).thenReturn(makePistas());
		Assertions.assertThrows(ParseException.class, () -> {
			pistaFormatter.parse("Fish", Locale.ENGLISH);
		});
	}
	
	private Collection<Pista> makePistas(){
		Collection<Pista> pistas = new ArrayList<>();
		Pista p1 = new Pista();
		Pista p2 = new Pista();
		p1.setName("Estadio de Sevilla");
		p2.setName("Estadio de Valencia");
		pistas.add(p1);
		pistas.add(p2);
		return pistas;
	}
}
