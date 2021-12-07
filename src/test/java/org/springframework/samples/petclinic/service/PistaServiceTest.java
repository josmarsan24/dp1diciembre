package org.springframework.samples.petclinic.service;



import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PistaServiceTest {

	@Autowired
	private PistaService pistaService;
	@Autowired
	private TorneoService torneoService;

	@Test
	public void testCountWithInitialData() {
		int count=pistaService.pistaCount();
		Iterable<Pista> pistas = pistaService.findAll();
		int count2 = 0;
		for (Pista a:pistas) {
			assertNotEquals(a, null);
			count2++;
		}
		assertEquals(count,5);
		assertEquals(count2,5);
	}
	
	@Test
	public void shouldDeletePista() {
		int count = pistaService.pistaCount();
		Pista p = this.pistaService.findPistaById(1);
		torneoService.borrarPistaTorneos(p);
		pistaService.delete(p);
		int count2 = pistaService.pistaCount();
		assertEquals(count-1, count2);
	}
	
	@Test
	public void shouldFindPistaById() {
		Pista p = this.pistaService.findPistaById(3);
		assertEquals(p.getName(), "Pista de Dos Hermanas de jabalina");
	}
	
	@Test
	@Transactional
	public void shouldInsertPista() {
		int count = this.pistaService.pistaCount();
		
		Pista p = new Pista();
		p.setName("Estadio Olimpico de Roma");
		p.setCiudad("Roma");
		p.setAforo(72698);
		
		this.pistaService.save(p);
		
		assertEquals(pistaService.findPistaById(p.getId()),p);
		assertEquals(this.pistaService.pistaCount(),count+1);
	}
	
	@Test
	@Transactional
	public void shouldUpdatePista() {
		Pista p = this.pistaService.findPistaById(1);
		Integer aforoAntiguo = p.getAforo();
		Integer aforoNuevo = 3000;
		
		p.setAforo(aforoNuevo);
		this.pistaService.save(p);
		
		p = this.pistaService.findPistaById(1);
		
		assertFalse(aforoAntiguo.equals(aforoNuevo));
		assertEquals(p.getAforo(),aforoNuevo);
	}
	
	@Test
	@Transactional
	public void shouldDeletePistaById() {
		this.pistaService.deletePistaById(1);
		try {
			pistaService.findPistaById(1);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void findDeporteTypes(){
		Set<Deporte> deportes = new HashSet<Deporte>();
		deportes.addAll(pistaService.findDeporteTypes());
		assertEquals(3, deportes.size());
	}
}
