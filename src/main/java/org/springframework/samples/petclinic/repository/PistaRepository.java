package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Pista;

public interface PistaRepository extends CrudRepository<Pista, Integer> {

}
