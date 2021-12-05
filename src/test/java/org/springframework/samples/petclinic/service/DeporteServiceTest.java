package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DeporteServiceTest {

	@Autowired
	private DeporteService deporteService;
	
	@Test
	public void testCountWithInitialData() {
		int count=deporteService.deporteCount();
		Iterable<Deporte> d = deporteService.findAll();
		int count2 = 0;
		for (Deporte a:d) {
			assertNotEquals(a, null);
			count2++;
		}
		assertEquals(count,3);
		assertEquals(count2,3);
	}

	@Test
	public void shouldFindDeporteById() {
		Deporte d = this.deporteService.findDeporteById(1).get();
		assertEquals(d.getName(), "100 metros lisos");
}


}
