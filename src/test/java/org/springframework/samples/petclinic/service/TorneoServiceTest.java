package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.samples.petclinic.service.exceptions.IncongruentSancionDateExcepcion;
import org.springframework.samples.petclinic.service.exceptions.IncongruentTorneoFinDateExcepcion;
import org.springframework.samples.petclinic.service.exceptions.IncongruentTorneoIniDateExcepcion;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TorneoServiceTest {
	@Autowired
	private TorneoService torneoService;
	@Autowired
	private AthleteService athleteService;
	
	@Test
	public void testConteoDatosIniciales() {
		int count=torneoService.torneoCount();
		Iterable<Torneo> Torneos = torneoService.findAll();
		int count2 = 0;
		for (Torneo a:Torneos) {
			assertNotEquals(a, null);
			count2++;
		}
		assertEquals(count,5);
		assertEquals(count2,5);
	}
	
	@Test
	public void deteteTorneo() {
		int count = torneoService.torneoCount();
		Torneo t = torneoService.findTorneoById(1);
		torneoService.delete(t);
		torneoService.deleteTorneoById(2);
		int count2 = torneoService.torneoCount();
		assertEquals(count-2,count2);
	}
	
	@Test
	public void saveTorneo() throws IncongruentTorneoIniDateExcepcion, IncongruentTorneoFinDateExcepcion {
		int count = torneoService.torneoCount();
		
		Torneo t = new Torneo();
		t.setFechaInicio(LocalDate.of(2020, 12, 23));
		Assertions.assertThrows(IncongruentTorneoIniDateExcepcion.class, () ->{
			torneoService.save(t);;
		});
		t.setFechaInicio(null);
		Assertions.assertThrows(IncongruentTorneoIniDateExcepcion.class, () ->{
			torneoService.save(t);;
		});
		t.setFechaInicio(LocalDate.of(2022, 12, 23));
		t.setFechaFin(LocalDate.of(2022, 11, 23));
		Assertions.assertThrows(IncongruentTorneoFinDateExcepcion.class, () ->{
			torneoService.save(t);;
		});
		
		t.setFechaFin(null);
		Assertions.assertThrows(IncongruentTorneoFinDateExcepcion.class, () ->{
			torneoService.save(t);;
		});
		
		t.setFechaInicio(LocalDate.of(2022, 12, 23));
		t.setFechaFin(LocalDate.of(2022, 12, 29));
		torneoService.save(t);
		int count2 = torneoService.torneoCount();
		
		assertEquals(count+1,count2);
	}
	

	@Test
	public void buscarParticipantes() {
		Torneo t = torneoService.findTorneoById(1);
		Set<Athlete> ats = torneoService.buscarParticipantes(t);
		int count = ats.size();
		assertEquals(count,1);
	}
	
	@Test
	public void buscarTorneosEnLosQueParticipan() {
		Set<Athlete> aths = new HashSet<Athlete>();
		aths.add(athleteService.findAthleteById(1));
		aths.add(athleteService.findAthleteById(2));
		aths.add(athleteService.findAthleteById(10));
		aths.add(athleteService.findAthleteById(4));
		Set<Torneo> torneos = torneoService.buscarTorneosEnLosQueParticipan(aths);
		int count = torneos.size();
		assertEquals(count,3);
	}
	
	@Test
	public void findPistaTypes() {
		Set<Pista> pistas = new HashSet<Pista>();
		pistas.addAll(torneoService.findPistaTypes());
		int count = pistas.size();
		assertEquals(count,5);
	}
}
