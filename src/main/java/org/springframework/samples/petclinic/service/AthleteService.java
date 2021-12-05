package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.AthleteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AthleteService {

	
	private AthleteRepository athleteRepository;
	private UserService userService;
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public AthleteService(AthleteRepository athleteRepository,UserService userService
			,AuthoritiesService authoritiesService) {
		this.athleteRepository = athleteRepository;
		this.userService = userService;
		this.authoritiesService= authoritiesService;
	}

	@Transactional
	public int athleteCount() {
		return (int) athleteRepository.count();
	}

	@Transactional
	public Iterable<Athlete> findAll() {
		log.info("Se van a obtener todos los atletas del sistema");
		return athleteRepository.findAll();
	}

	@Transactional
	public void delete(Athlete athlete) {
		if(athlete.getUser()!=null) {
			User user = athlete.getUser();
			athlete.setUser(null);
			if(user.getAuthorities()!=null) {
				Set<Authorities> authorities= user.getAuthorities();
				user.setAuthorities(null);
				authoritiesService.deleteAll(authorities);
				}
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
		log.info("Se van a obtener los datos del deportista con ID "+ id);
		return athleteRepository.findById(id).get();
	}

	@Transactional
	public Set<Athlete> findAthleteByEntrenadorId(int entrenadorId) {
		log.info("Se van a obtener los datos del deportista cuyo entrenador tiene ID "+entrenadorId);
		return athleteRepository.findByEntrenadorId(entrenadorId);
	}

	@Transactional
	public void eliminarEntrenadorDeAtleta(int i) {
		athleteRepository.eliminarEntrenadorDeAtleta(i);
		log.info("Se ha eliminado el entrenador del deportista indicado");
	}

	@Transactional
	public Set<Athlete> buscarAtletaSinEntrenador() {
		log.info("Se van a obtener todos los atletas que no tienen entrenador");
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
		athleteRepository.save(a);		
		userService.saveUser(a.getUser());
		authoritiesService.saveAuthorities(a.getUser().getUsername(), "deportista");
	}
	
	@Transactional(readOnly = true)
	public Collection<Deporte> findDeporteTypes() throws DataAccessException {
		return athleteRepository.findDeporteTypes();
	}
		
}


