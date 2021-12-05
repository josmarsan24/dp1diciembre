package org.springframework.samples.petclinic.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.service.AthleteService;

@ExtendWith(MockitoExtension.class)
public class DeporteFormatterTests {
	@Mock
	private AthleteService athleteService;
	
	private DeporteFormatter deporteFormatter;
	
	@BeforeEach
	void setup() {
		deporteFormatter = new DeporteFormatter(athleteService);
	}

	@Test
	void testPrint() {
		Deporte deporte = new Deporte();
		deporte.setName("1000 metros lisos");
		String deporteName = deporteFormatter.print(deporte, Locale.ENGLISH);
		assertEquals("1000 metros lisos", deporteName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(athleteService.findDeporteTypes()).thenReturn(makeDeportes());
		Deporte deporte = deporteFormatter.parse("500 metros relevos", Locale.ENGLISH);
		assertEquals("500 metros relevos", deporte.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(athleteService.findDeporteTypes()).thenReturn(makeDeportes());
		Assertions.assertThrows(ParseException.class, () -> {
			deporteFormatter.parse("Fish", Locale.ENGLISH);
		});
	}

	private Collection<Deporte> makeDeportes() {
		Collection<Deporte> deportes = new ArrayList<>();
		deportes.add(new Deporte() {
			{
				setName("400 metros vallas");
			}
		});
		deportes.add(new Deporte() {
			{
				setName("500 metros relevos");
			}
		});
		return deportes;
	}
}
