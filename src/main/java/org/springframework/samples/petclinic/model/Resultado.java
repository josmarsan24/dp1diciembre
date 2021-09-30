package org.springframework.samples.petclinic.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "resultados")
public class Resultado extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "torneo_id")
	private Torneo torneo;
	@ManyToOne
	@JoinColumn(name = "athlete_id")
	private Athlete atleta;

	@Column(name = "posicion") 
	private Integer posicion;

	public Resultado() {
		super();
	}

	public Resultado(Torneo torneo, Athlete atleta, Integer posicion) {
		super();
		this.torneo = torneo;
		this.atleta = atleta;
		this.posicion = posicion;
	}

}
