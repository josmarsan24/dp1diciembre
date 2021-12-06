package org.springframework.samples.petclinic.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.EntrenadorRepository;
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
	
	@Transactional(rollbackFor = Exception.class)
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
		if(user.getAuthorities()!=null) {
			Set<Authorities> authorities= user.getAuthorities();
			user.setAuthorities(null);
			authoritiesService.deleteAll(authorities);
			}
		userService.delete(user);
		}
		entrenador.setAthletes(new HashSet<Athlete>());
		entrenadorRepository.delete(entrenador);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void save(Entrenador entrenador) {
		entrenadorRepository.save(entrenador);
		
	}
	
	@Transactional
	public Entrenador findEntrenadorById(int entrenadorId) {
		
		return entrenadorRepository.findById(entrenadorId).get();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteEntrenadorById(int entrenadorId) {
		
		entrenadorRepository.deleteById(entrenadorId);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void saveUser(Entrenador e) {
		entrenadorRepository.save(e);		
		userService.saveUser(e.getUser());
		authoritiesService.saveAuthorities(e.getUser().getUsername(), "entrenador");
		
	}
	
}
