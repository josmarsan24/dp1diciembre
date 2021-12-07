package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.transaction.annotation.Transactional;

public interface EntrenadorRepository extends CrudRepository<Entrenador, Integer>{
	@Transactional
	@Query("SELECT pdeporte FROM Deporte pdeporte ORDER BY pdeporte.name")
	List<Deporte> findDeporteTypes() throws DataAccessException; 
}
