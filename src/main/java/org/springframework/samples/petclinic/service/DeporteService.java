package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.repository.DeporteRepository;
import org.springframework.stereotype.Service;

@Service
public class DeporteService {

	@Autowired
	private DeporteRepository deporteRepo;
	
	@Transactional
	public int deporteCount() {
		return (int) deporteRepo.count();
	}
	
	@Transactional
	public Iterable<Deporte> findAll(){
		return deporteRepo.findAll();
	}
	
	@Transactional
	public Optional<Deporte> findDeporteById(int id){
		return deporteRepo.findById(id);
	}
}
