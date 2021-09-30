package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Sancion;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.AthleteRepository;
import org.springframework.samples.petclinic.repository.SancionRepository;
import org.springframework.samples.petclinic.service.exceptions.NotValidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.NotValidUsernameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AthleteService {

	private AthleteRepository athleteRepository;
	private SancionRepository sancionRepo;
	private UserService userService;
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public AthleteService(AthleteRepository athleteRepository,
			SancionRepository sancionRepository,UserService userService,AuthoritiesService authoritiesService) {
		this.athleteRepository = athleteRepository;
		this.sancionRepo = sancionRepository;
		this.userService = userService;
		this.authoritiesService= authoritiesService;
	}

	@Transactional
	public int athleteCount() {
		return (int) athleteRepository.count();
	}

	@Transactional
	public Iterable<Athlete> findAll() {
		return athleteRepository.findAll();
	}

	@Transactional
	public void delete(Athlete athlete) {
		if(athlete.getUser()!=null) {
			User user = athlete.getUser();
			athlete.setUser(null);
			userService.delete(user);
			}
		if(athlete.getTorneos()!=null) {
			Set<Torneo> torneos = athlete.getTorneos();
			for (Torneo t : torneos) {
				Set<Athlete> athletes = t.getParticipantes();
				athletes.remove(athlete);
				t.setParticipantes(athletes);
			}
		}
		athleteRepository.delete(athlete);
	}

	@Transactional
	public void save(Athlete athlete) {
		athleteRepository.save(athlete);

	}

	@Transactional
	public void deleteAthleteById(int athleteId) {
		athleteRepository.deleteById(athleteId);
	}

	@Transactional(readOnly = true)
	public Athlete findAthleteById(int id) throws DataAccessException {
		return athleteRepository.findById(id).get();
	}

	@Transactional
	public Set<Athlete> findAthleteByEntrenadorId(int entrenadorId) {
		return athleteRepository.findByEntrenadorId(entrenadorId);
	}

	@Transactional
	public void eliminarEntrenadorDeAtleta(int i) {
		athleteRepository.eliminarEntrenadorDeAtleta(i);
	}

	@Transactional
	public Set<Athlete> buscarAtletaSinEntrenador() {
		return athleteRepository.buscarAtletaSinEntrenador();
	}
	
	@Transactional
	public Set<Athlete> buscarAtletaConMiDeporteSinEntrenador(Deporte d) {
		Set<Athlete> aths =athleteRepository.buscarAtletaSinEntrenador();
		Set<Athlete> res = new HashSet<Athlete>();
		for(Athlete a:aths) {
			if(a.getDeporte().equals(d)) {
				res.add(a);
			}
		}
		return res;
	}

	@Transactional
	public void añadirEntrenadorDeAtleta(int athleteId, int entrenadorId) {
		athleteRepository.añadirEntrenadorDeAtleta(athleteId, entrenadorId);
	}
	
	@Transactional
	public void saveUser(Athlete a){
		//creando deportista
		athleteRepository.save(a);		
		//creating user
		userService.saveUser(a.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(a.getUser().getUsername(), "deportista");
		
	}
	
	@Transactional(readOnly = true)
	public Collection<Deporte> findDeporteTypes() throws DataAccessException {
		return athleteRepository.findDeporteTypes();
	}
		
}


