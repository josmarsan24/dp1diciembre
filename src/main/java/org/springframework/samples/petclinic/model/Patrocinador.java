package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "patrocinadores")
public class Patrocinador extends NamedEntity{
	
	@NotBlank
	String tipo;
	
	String twitter;
	
	String instagram;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "patrocinador")
	private Set<Athlete> athletas;
}
