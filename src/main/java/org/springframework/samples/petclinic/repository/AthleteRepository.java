package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.transaction.annotation.Transactional;

public interface AthleteRepository extends CrudRepository<Athlete, Integer>{
	@Query("SELECT athlete from Athlete athlete WHERE entrenador_id LIKE :entrenadorId%")
	Set<Athlete> findByEntrenadorId(@Param("entrenadorId")int entrenadorId);
		
	@Transactional
	@Modifying
	@Query("UPDATE Athlete SET entrenador_id=null where id LIKE :athleteId")
	void eliminarEntrenadorDeAtleta(@Param("athleteId") int athleteId);

	@Query("SELECT athlete from Athlete athlete WHERE entrenador_id IS NULL")
	Set<Athlete> buscarAtletaSinEntrenador();

	@Transactional
	@Modifying
	@Query("UPDATE Athlete SET entrenador_id=:entrenadorId where id LIKE :athleteId")
	void añadirEntrenadorDeAtleta(@Param("athleteId") int athleteId,@Param("entrenadorId") int entrenadorId);
	
	@Query("SELECT pdeporte FROM Deporte pdeporte ORDER BY pdeporte.name")
	List<Deporte> findDeporteTypes() throws DataAccessException;
	
}
