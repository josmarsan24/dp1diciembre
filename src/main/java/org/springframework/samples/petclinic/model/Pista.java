package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Table(name = "pistas")
@Entity
@Getter
@Setter
public class Pista extends NamedEntity {

	@Min(100)
	@NotNull
	private Integer aforo;
	@NotBlank
	private String ciudad;
}
