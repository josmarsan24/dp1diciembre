package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.repository.PistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PistaService {

	@Autowired
	private PistaRepository pistaRepo;
	
	@Transactional
	public int pistaCount() {
		return (int) pistaRepo.count();
	}
	
	@Transactional
	public Iterable<Pista> findAll() {
		return pistaRepo.findAll();
		}
	@Transactional
	public void delete(Pista pista) {
		pistaRepo.delete(pista);
	}
	@Transactional
	public void save(Pista pista) {
		pistaRepo.save(pista);
		
	}
	@Transactional
	public Pista findPistaById(int pistaId) {
		
		return pistaRepo.findById(pistaId).get();
	}
	
	public void deletePistaById(int pistaId) {
		
		pistaRepo.deleteById(pistaId);
	}
	
}