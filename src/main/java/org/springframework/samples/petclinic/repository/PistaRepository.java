package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Pista;

public interface PistaRepository extends CrudRepository<Pista, Integer> {

}
