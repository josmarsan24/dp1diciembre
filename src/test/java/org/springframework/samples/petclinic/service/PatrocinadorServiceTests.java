package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PatrocinadorServiceTests {
	
	@Autowired
	private PatrocinadorService patrocinadorService;
	
	@Test
	public void testCountWithInitialData() {
		int count=patrocinadorService.patrocinadorCount();
		Iterable<Patrocinador> p = patrocinadorService.findAll();
		int count2 = 0;
		for (Patrocinador a:p) {
			assertNotEquals(a, null);
			count2++;
		}
		assertEquals(count,3);
		assertEquals(count2,3);
	}
	
	@Test
	public void shouldFindPatrocinadorById() {
		Patrocinador p = this.patrocinadorService.findPatrocinadorById(1).get();
		assertEquals(p.getName(), "Powerade");
	}
	
}

