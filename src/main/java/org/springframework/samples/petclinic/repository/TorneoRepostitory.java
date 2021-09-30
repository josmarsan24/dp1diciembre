package org.springframework.samples.petclinic.repository;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.model.Torneo;


public interface TorneoRepostitory  extends CrudRepository<Torneo, Integer>{

	@Query("SELECT pdeporte FROM Deporte pdeporte ORDER BY pdeporte.name")
	List<Deporte> findDeporteTypes() throws DataAccessException;

	@Query("SELECT ppista FROM Pista ppista ORDER BY ppista.name")
	List<Pista> findPistaTypes() throws DataAccessException;

}
