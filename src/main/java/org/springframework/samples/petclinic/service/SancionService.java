package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Sancion;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.samples.petclinic.repository.AthleteRepository;
import org.springframework.samples.petclinic.repository.SancionRepository;
import org.springframework.samples.petclinic.service.exceptions.IncongruentSancionDateExcepcion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SancionService {
	
	private SancionRepository sancionRepo;
	
	@Autowired
	public SancionService(SancionRepository sancionRepository,
			AthleteRepository deportistaRepository) {
		this.sancionRepo = sancionRepository;
	}
	
	@Transactional
	public Iterable<Sancion> findAll() {
		return sancionRepo.findAll();
		}
	
	@Transactional
	public int sancCount() {
		return (int) sancionRepo.count();
	}
	
	@Transactional
	public boolean esSancionado(int athleteId) {
		boolean sancionado = false;
		LocalDate fechaActual = LocalDate.now();
		Set<Sancion> sanciones=findSancionByAthleteId(athleteId);
		if(!sanciones.isEmpty()) {
			for(Sancion s:sanciones) {
				if (s.getFechaFin()!=null) {
					if(s.getFechaFin().isAfter(fechaActual)) {
						sancionado = true;
					}
				}
			}
		}
		return sancionado;
	}
	
	@Transactional
	Set<Sancion> findSancionByAthleteId(int athleteId) {
		return sancionRepo.findByAthleteId(athleteId);
	}
	
	@Transactional
	public Sancion findSancionById(int sancionId) throws DataAccessException {
		
		return sancionRepo.findById(sancionId).get();
	}
	
	@Transactional (rollbackFor = IncongruentSancionDateExcepcion.class)
	public void saveSancion(Sancion sancion) throws DataAccessException, IncongruentSancionDateExcepcion {
		if(sancion.getFechaFin()==null) {throw new IncongruentSancionDateExcepcion();}
		else{
			if (!sancion.getFechaFin().isAfter(LocalDate.now())) {
			throw new IncongruentSancionDateExcepcion();
		}else {
			sancionRepo.save(sancion);
			Athlete a = sancion.getAthlete();
			a.addSancion(sancion);
			List<Torneo> torneosAux = new ArrayList<Torneo>();
			if(a.getTorneos()!=null) {
				Set<Torneo> torneos = a.getTorneos();
				for(Torneo t:torneos) {
				//eliminar a los deportistas de los torneos que aun no han empezado 
				//	y que terminan antes de que acabe la sancion
					if(t.getFechaInicio().isAfter(LocalDate.now())&&t.getFechaFin().isBefore(sancion.getFechaFin())) {
						Set<Athlete> participantes = t.getParticipantes();
						participantes.remove(a);
						t.setParticipantes(participantes);
						torneosAux.add(t);
						}
					}
				torneos.removeAll(torneosAux);
				a.setTorneos(torneos);
				}
			}
		}
	}
	
	@Transactional
	public void deleteSancion(Sancion sancion) throws DataAccessException{
		Athlete a=sancion.getAthlete();
		a.getSanciones().remove(sancion);
		sancionRepo.delete(sancion);
	}
}

