package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Transactional(rollbackFor = Exception.class)
	public void delete(Pista pista) {
		pistaRepo.delete(pista);
	}
	@Transactional(rollbackFor = Exception.class)
	public void save(Pista pista) {
		pistaRepo.save(pista);
		
	}
	@Transactional
	public Pista findPistaById(int pistaId) {
		
		return pistaRepo.findById(pistaId).get();
	}
	@Transactional(rollbackFor = Exception.class)
	public void deletePistaById(int pistaId) {
		
		pistaRepo.deleteById(pistaId);
	}
	
}
