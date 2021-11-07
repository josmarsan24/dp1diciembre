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
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.EntrenadorRepository;
import org.springframework.samples.petclinic.service.exceptions.NotValidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.NotValidUsernameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntrenadorService {
	
	
	@Autowired
	private EntrenadorRepository entrenadorRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private AthleteService athleteService;
	
	@Transactional
	public int entrenadorCount() {
		return (int) entrenadorRepository.count();
	}
	
	@Transactional
	public Iterable<Entrenador> findAll() {
		return entrenadorRepository.findAll();
		}
	
	@Transactional
	public void delete(Entrenador entrenador) {
		Set<Athlete> deportistas = athleteService.findAthleteByEntrenadorId(entrenador.getId());
		if (!deportistas.isEmpty()) {
			for (Athlete d:deportistas) {
				d.setEntrenador(null);
			}
		}
		if(entrenador.getUser()!=null) {
		User user = entrenador.getUser();
		entrenador.setUser(null);
		userService.delete(user);
		}
		entrenador.setAthletes(new HashSet<Athlete>());
		entrenadorRepository.delete(entrenador);
	}
	
	@Transactional
	public void save(Entrenador entrenador) {
		entrenadorRepository.save(entrenador);
		
	}
	
	@Transactional
	public Entrenador findEntrenadorById(int entrenadorId) {
		
		return entrenadorRepository.findById(entrenadorId).get();
	}
	
	@Transactional
	public void deleteEntrenadorById(int entrenadorId) {
		
		entrenadorRepository.deleteById(entrenadorId);
	}
	
	@Transactional
	public void saveUser(Entrenador e) {
		//creando entrenador
		entrenadorRepository.save(e);		
		//creating user
		userService.saveUser(e.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(e.getUser().getUsername(), "entrenador");
		
	}
	
}