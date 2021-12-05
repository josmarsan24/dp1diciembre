package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Resultado;
import org.springframework.samples.petclinic.repository.ResultadoRepository;
import org.springframework.samples.petclinic.service.exceptions.IncrongruentPositionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import jdk.internal.jline.internal.Log;
import lombok.extern.slf4j.Slf4j;
@Slf4j
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

	@Transactional(rollbackFor = IncrongruentPositionException.class)
	public void save(Resultado r) throws DataAccessException, IncrongruentPositionException{
		if (r.getPosicion() > r.getTorneo().getParticipantes().size() || r.getTorneo().getResultados().stream().anyMatch(x->x.getPosicion()==r.getPosicion())) {
			throw new IncrongruentPositionException();
		}
		resultadoRepo.save(r);
	}

	@Transactional
	public Resultado findById(int id) {
		log.info("Se ha obtenido el resultado con ID "+id);
		return resultadoRepo.findById(id).get();
	}
}
