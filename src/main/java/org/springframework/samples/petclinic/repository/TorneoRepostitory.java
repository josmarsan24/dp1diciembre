package org.springframework.samples.petclinic.repository;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.transaction.annotation.Transactional;


public interface TorneoRepostitory  extends CrudRepository<Torneo, Integer>{
	
	@Transactional
	@Query("SELECT pdeporte FROM Deporte pdeporte ORDER BY pdeporte.name")
	List<Deporte> findDeporteTypes() throws DataAccessException;
	
	@Transactional
	@Query("SELECT ppista FROM Pista ppista ORDER BY ppista.name")
	List<Pista> findPistaTypes() throws DataAccessException;

}
