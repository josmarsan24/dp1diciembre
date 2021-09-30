package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.IncongruentSancionDateExcepcion;
import org.springframework.samples.petclinic.service.exceptions.NotValidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.NotValidUsernameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EntrenadorServiceTest {
	
	@Autowired
	private EntrenadorService entrenadorService;
	
	@Autowired
	private AthleteService athleteService;
	
	@Test
	public void testCountWithInitialData() {
		int count=entrenadorService.entrenadorCount();
		Iterable<Entrenador> e = entrenadorService.findAll();
		int count2 = 0;
		for (Entrenador a:e) {
			assertNotEquals(a, null);
			count2++;
		}
		
		assertEquals(count,5);
		assertEquals(count2,5);
	}
	
	@Test
	void shouldFindEntrenadorWithCorrectId() {
		Entrenador e = this.entrenadorService.findEntrenadorById(2);
		assertThat(e.getNombre()).startsWith("Rosa");

	}
	
	@Test
	@Transactional
	public void shouldInsertEntrenador() {
		
		int count=this.entrenadorService.entrenadorCount();

		Entrenador ent = new Entrenador();
		ent.setNombre("David");
		ent.setApellidos("Muñoz");     
		ent.setDni("34534623G"); 
                
		this.entrenadorService.save(ent);

		assertThat(this.entrenadorService.entrenadorCount()).isEqualTo(count + 1);
	}
	
	@Test
	@Transactional
	void shouldUpdateEntrenador() {
		Entrenador e = this.entrenadorService.findEntrenadorById(1);
		String oldLastName = e.getApellidos();
		String newLastName = oldLastName + "Fernández";

		e.setApellidos(newLastName);
		this.entrenadorService.save(e);

		e = this.entrenadorService.findEntrenadorById(1);
		assertThat(e.getApellidos()).isEqualTo(newLastName);
	}
	
	@Test
	@Transactional
	void shouldDeleteEntrenadorById() {		
		this.entrenadorService.deleteEntrenadorById(1);
		try {
			entrenadorService.findEntrenadorById(1);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteEntrenadorConEntrenados(){
		Set<Athlete>ats = this.athleteService.findAthleteByEntrenadorId(1);
		Entrenador entrenador = entrenadorService.findEntrenadorById(1);
		this.entrenadorService.delete(entrenador);
		for (Athlete a : ats) {
			assertEquals(a.getEntrenador(), null);
		}
		Assertions.assertThrows(NoSuchElementException.class, () ->{
			entrenadorService.findEntrenadorById(1);
		});	
	}
	
	@Test
	public void testSaveUser() {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		
		Entrenador ent = new Entrenador();
		ent.setNombre("David");
		ent.setApellidos("Muñoz");     
		ent.setDni("34534623G"); 
		ent.setUser(user);
		
		int count= entrenadorService.entrenadorCount();
		entrenadorService.saveUser(ent);
		int count2= entrenadorService.entrenadorCount();
		assertEquals(count+1, count2);
	}
	
	@Test
	public void testDeleteEntrenadorUser() {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		
		Entrenador a = this.entrenadorService.findEntrenadorById(1);
		a.setUser(user);
		
		int count=entrenadorService.entrenadorCount();
		entrenadorService.delete(a);
		int count2=entrenadorService.entrenadorCount();
		assertEquals(count-1,count2);
	}
}