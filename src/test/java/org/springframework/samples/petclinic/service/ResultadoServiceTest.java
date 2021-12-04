package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.model.Resultado;
import org.springframework.samples.petclinic.service.exceptions.IncrongruentPositionException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ResultadoServiceTest {
	
	@Autowired
	private ResultadoService resultadoService;
	
	@Autowired
	private AthleteService athleteService;
	
	@Autowired
	private TorneoService torneoService;
	
	@Test
	public void testCountWithInitialData() {
		int count=resultadoService.ResultadoCount();
		Iterable<Resultado> resultados = resultadoService.findAll();
		int count2 = 0;
		for (Resultado a:resultados) {
			assertNotEquals(a, null);
			count2++;
		}
		assertEquals(count,5);
		assertEquals(count2,5);
	}
	
	@Test
	public void testfindbyId() {
		Resultado r = resultadoService.findById(1);
		assertEquals(r.getAtleta().getNombre(), "Lucas");
	}
	
	@Test
	public void testSaveResultado() {
		Resultado r=new Resultado();
		r.setId(10);
		r.setAtleta(athleteService.findAthleteById(13));
		r.setTorneo(torneoService.findTorneoById(2));
		r.setPosicion(5);
		try {
			resultadoService.save(r);
		} catch (DataAccessException | IncrongruentPositionException e) {
		}
		
		Assertions.assertThrows(IncrongruentPositionException.class, ()->{
			resultadoService.save(r);
		});
		
		r.setPosicion(1);
		try {
			resultadoService.save(r);
		} catch (DataAccessException | IncrongruentPositionException e) {
		}
		
		Assertions.assertThrows(IncrongruentPositionException.class, ()->{
			resultadoService.save(r);
		});
		
		r.setPosicion(4);
		try {
			resultadoService.save(r);
		} catch (DataAccessException | IncrongruentPositionException e) {
		}
		int c = resultadoService.ResultadoCount();
		assertEquals(c,6);
	}
	
}
