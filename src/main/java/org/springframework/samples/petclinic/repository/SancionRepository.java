package org.springframework.samples.petclinic.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Sancion;
import org.springframework.transaction.annotation.Transactional;

public interface SancionRepository extends CrudRepository<Sancion, Integer>{
	@Transactional
	@Query("SELECT sancion from Sancion sancion WHERE athlete_id LIKE :athleteId%")
	Set<Sancion> findByAthleteId(@Param("athleteId")int athleteId);
}
