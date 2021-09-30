package org.springframework.samples.petclinic.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.samples.petclinic.model.Genero;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.service.exceptions.IncongruentSancionDateExcepcion;
import org.springframework.samples.petclinic.service.exceptions.NotValidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.NotValidUsernameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javassist.expr.NewArray;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AthleteServiceTest {
	
	@Autowired
	private AthleteService athleteService;

	@Autowired
	private UserService userService;
	
	@Test
	public void testConteoDatosIniciales() {
		int count=athleteService.athleteCount();
		Iterable<Athlete> athletes = athleteService.findAll();
		int count2 = 0;
		for (Athlete a:athletes) {
			assertNotEquals(a, null);
			count2++;
		}
		assertEquals(count,15);
		assertEquals(count2,15);
	}
	
	@Test
	public void deleteAthlete() {
		Athlete athlete = athleteService.findAthleteById(4);
		athleteService.delete(athlete);
		athleteService.deleteAthleteById(2);
		int count=athleteService.athleteCount();
		assertEquals(count,13);
	}

	@Test
	public void testObtenerAtletasPorEntrenador() {
		Set<Athlete> atletas = this.athleteService.findAthleteByEntrenadorId(1);
		assertEquals(3,atletas.size());
	}
	
	@Test
	public void testObtenerAtletasSinEntrenadorMismoDeporte() {
		Athlete a = athleteService.findAthleteById(4);
		Deporte d = a.getDeporte();
		Set<Athlete> atletas = athleteService.buscarAtletaConMiDeporteSinEntrenador(d);
		assertEquals(2,atletas.size());
	}

	@Test
	public void añadirEntrenadorDeAtleta() {
		Set<Athlete> atletas = this.athleteService.findAthleteByEntrenadorId(2);
		assertEquals(1,atletas.size());
		athleteService.añadirEntrenadorDeAtleta(13, 2);
		atletas = this.athleteService.findAthleteByEntrenadorId(2);
		assertEquals(2,atletas.size());
	}
	
	@Test
	public void testEliminarEntrenadorDeAtleta() {
		this.athleteService.eliminarEntrenadorDeAtleta(1);
		int count=athleteService.athleteCount();
		Athlete atleta = this.athleteService.findAthleteById(1);
		assertEquals(null,atleta.getEntrenador());
		assertEquals(count,15);
	}
	
	@Test
	public void findDeporteTypes(){
		Set<Deporte> deportes = new HashSet<Deporte>();
		deportes.addAll(athleteService.findDeporteTypes());
		assertEquals(3, deportes.size());
	}
	
	
	@Test
	public void testEliminarAtletaNoExistente() {
		Integer ID = 50;
		Integer nAtletas=athleteService.athleteCount();
		try {
			athleteService.deleteAthleteById(ID);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		Assertions.assertThrows(EmptyResultDataAccessException.class, () ->{
			athleteService.deleteAthleteById(ID);
		});
		assertThat(athleteService.athleteCount()).isEqualTo(nAtletas);
	}
	
	
	@Test
	void testObtenerAtletaPorID() {
		Athlete e = this.athleteService.findAthleteById(1);
		assertThat(e.getNombre()).startsWith("Lucas");

	}
	
	@Test
	void testObtenerAtletaNoExistentePorID() {
		Integer ID = 50;
		try {
			Athlete e = athleteService.findAthleteById(ID);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		Assertions.assertThrows(NoSuchElementException.class, () ->{
			athleteService.findAthleteById(ID);
		});

	}
	
	
	@Test
	void testObtenerAtletasSinEntrenador() {
		Set<Athlete> e = this.athleteService.buscarAtletaSinEntrenador();
		assertEquals(e.size(),7);

	}
	
	@Test
	@Transactional
	public void testAñadirAtleta() {
		int count=this.athleteService.athleteCount();
		Athlete ath = new Athlete();
		ath.setNombre("David");
		ath.setApellidos("Muñoz");     
		ath.setDni("77788349V");   
		ath.setGenero(Genero.HOMBRE);
		ath.setWeight(80);
		this.athleteService.save(ath);
		assertThat(this.athleteService.athleteCount()).isEqualTo(count + 1);
	}
	
	@Test
	@Transactional
	void testActualizarAtleta() {
		Athlete ath = this.athleteService.findAthleteById(1);
		String oldLastName = ath.getApellidos();
		String newLastName = oldLastName + "Fernández";

		ath.setApellidos(newLastName);
		this.athleteService.save(ath);

		ath = this.athleteService.findAthleteById(1);
		assertThat(ath.getApellidos()).isEqualTo(newLastName);
	}
	
	@Test
	public	void testSaveUser(){
		Athlete a = new Athlete();
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		
		a.setUser(user);
		a.setNombre("Nombre");
		a.setApellidos("apellidos");
		a.setDni("12312312G");
		a.setGenero(Genero.HOMBRE);
		a.setWeight(80);
		int count= athleteService.athleteCount();
		athleteService.saveUser(a);
		int count2= athleteService.athleteCount();
		assertEquals(count+1, count2);
	}
	
	@Test
	public	void deleteAthleteUser() {
		
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		
		User user2 = new User();
		user2.setUsername("username2");
		user2.setPassword("password2");
		
		Authorities aut = new Authorities();
		Set<Authorities> authorities = new HashSet<Authorities>();
		authorities.add(aut);
		user2.setAuthorities(authorities);
		
		Athlete a = this.athleteService.findAthleteById(1);
		Athlete a2 = this.athleteService.findAthleteById(2);
		a.setUser(user);
		a2.setUser(user2);
		
		int count=athleteService.athleteCount();
		athleteService.delete(a);
		athleteService.delete(a2);
		int count2=athleteService.athleteCount();
		assertEquals(count-2,count2);
	}
	
	@Test
	public void testValidUser() throws NotValidUsernameException, NotValidPasswordException {
		User user = new User();
		user.setUsername("");
		Assertions.assertThrows(NotValidUsernameException.class, () ->{
			userService.validUser(user);
		});
		
		user.setUsername("username");
		user.setPassword("");
		Assertions.assertThrows(NotValidPasswordException.class, () ->{
			userService.validUser(user);
		});
		
		user.setUsername("e1"); //ya existe este usuario
		user.setPassword("password");
		Assertions.assertThrows(NotValidUsernameException.class, () ->{
			userService.validUser(user);
		});
		
		user.setUsername("usuario");
		user.setPassword("password");
		userService.validUser(user);
		
	}
	
}

