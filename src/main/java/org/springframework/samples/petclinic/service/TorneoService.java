package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.samples.petclinic.repository.TorneoRepostitory;
import org.springframework.samples.petclinic.service.exceptions.IncongruentTorneoFinDateExcepcion;
import org.springframework.samples.petclinic.service.exceptions.IncongruentTorneoIniDateExcepcion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import jdk.internal.jline.internal.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TorneoService {

	@Autowired
	private TorneoRepostitory torneoRepo;
	
	@Autowired
	private AthleteService athleteService;
	
	@Autowired
	private SancionService sancionService;

	@Transactional
	public int torneoCount() {
		return (int) torneoRepo.count();
	}

	@Transactional
	public Iterable<Torneo> findAll() {
		return torneoRepo.findAll();
	}
	@Transactional(rollbackFor = Exception.class)
	public void delete(Torneo torneo) {
		torneoRepo.delete(torneo);
	}
	@Transactional(rollbackFor = Exception.class)
	public void save(Torneo torneo) throws IncongruentTorneoIniDateExcepcion, IncongruentTorneoFinDateExcepcion {
		if(torneo.getFechaInicio()==null||torneo.getFechaInicio().isBefore(LocalDate.now())) {
			throw new IncongruentTorneoIniDateExcepcion();
		}else { 
		if(torneo.getFechaFin()==null||torneo.getFechaFin().isBefore(torneo.getFechaInicio())) {
			throw new IncongruentTorneoFinDateExcepcion();
		}else {
		torneoRepo.save(torneo);
			}
		}
	}
	@Transactional
	public Torneo findTorneoById(int torneoId) {

		return torneoRepo.findById(torneoId).get();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteTorneoById(int torneoId) {

		torneoRepo.deleteById(torneoId);
	}

	@Transactional(readOnly = true)
	public Collection<Pista> findPistaTypes() throws DataAccessException {
		return torneoRepo.findPistaTypes();
	}
	
	@Transactional
	public Set<Athlete> buscarParticipantes(Torneo torneo) {
		Iterable<Athlete> athletes = athleteService.findAll();
		Set<Athlete> res = new HashSet<Athlete>();
		Deporte d = torneo.getDeporte();
		for (Athlete a : athletes) {
			if(!sancionService.esSancionado(a.getId())) {
				if (a.getDeporte().equals(d)) {
					if (!a.getTorneos().contains(torneo)) {
						res.add(a);
					}
				}
			}
		}
		return res;
	}
	
	
	@Transactional
	public Set<Torneo> buscarTorneosEnLosQueParticipan(Set<Athlete> athletes) {
		Set<Torneo> torneos = new HashSet<Torneo>();
		for (Athlete a : athletes) {
			torneos.addAll(a.getTorneos());
		}
		return torneos;
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void borrarPistaTorneos(Pista pista) {
		Iterable<Torneo> torneos = findAll();
		for (Torneo t : torneos) {
			if (t.getPista()!=null) {
				if(t.getPista().equals(pista)) {
					t.setPista(null);
				}
			}
		}
		log.info("Se ha eliminado la pista "+pista.getName());
		
	}
	
}
