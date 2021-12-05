package org.springframework.samples.petclinic.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.repository.PatrocinadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatrocinadorService {
	@Autowired
	private PatrocinadorRepository patrocinadorRepo;
	
	@Transactional
	public int patrocinadorCount() {
		return (int) patrocinadorRepo.count();
	}
	
	@Transactional
	public Iterable<Patrocinador> findAll(){
		return patrocinadorRepo.findAll();
	}
	
	@Transactional
	public Optional<Patrocinador> findPatrocinadorById(int id){
		return patrocinadorRepo.findById(id);
	}
	
	@Transactional
	public Set<Patrocinador> findPatrocinadorTypes() {
		Set<Patrocinador> res = new HashSet<Patrocinador>();
		Iterable<Patrocinador> patrocinadores = findAll();
		for (Patrocinador p : patrocinadores) {
			res.add(p);
		}
		System.out.println(res);
		return res;
	}
	
}
