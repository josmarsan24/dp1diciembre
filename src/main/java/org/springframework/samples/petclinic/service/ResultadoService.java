package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Resultado;
import org.springframework.samples.petclinic.repository.ResultadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultadoService {

	@Autowired
	private ResultadoRepository resultadoRepo;

	@Transactional
	public int ResultadoCount() {
		return (int) resultadoRepo.count();
	}

	@Transactional
	public Iterable<Resultado> findAll() {
		return resultadoRepo.findAll();
	}

	@Transactional
	public void save(Resultado r) {
		resultadoRepo.save(r);
	}

	@Transactional
	public Resultado findById(int id) {
		return resultadoRepo.findById(id).get();
	}
}
