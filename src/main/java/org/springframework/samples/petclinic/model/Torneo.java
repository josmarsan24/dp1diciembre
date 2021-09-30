package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.SancionService;
import org.springframework.samples.petclinic.service.exceptions.AddParticipanteSancionadoException;
import org.springframework.samples.petclinic.service.exceptions.IncongruentSancionDateExcepcion;
import org.springframework.transaction.annotation.Transactional;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "torneos")
public class Torneo extends NamedEntity{

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "torneo_participantes", joinColumns = @JoinColumn(name = "torneo_id"),
			inverseJoinColumns = @JoinColumn(name = "athlete_id"))
	private Set<Athlete> participantes;
	
	@Column(name = "fecha_inicio")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaInicio;
	
	@Column(name = "fecha_fin")   
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaFin;
	
	@ManyToOne
	@JoinColumn(name = "pista_id")
	private Pista pista;
	@ManyToOne
	@JoinColumn(name = "deporte_id")
	private Deporte deporte;
	
	protected Set<Athlete> getParticipantesInternal() {
		if (this.participantes == null) {
			this.participantes = new HashSet<>();
		}
		return this.participantes;
	}
	
	protected void setParticipantesInternal(Set<Athlete> participantes) {
		this.participantes = participantes;
	}
	
	public void addParticipante(Athlete a){
		Set<Athlete> athletes = new HashSet<Athlete>();
		athletes.addAll(getParticipantesInternal());
		athletes.add(a);
		setParticipantes(athletes);
	}
}
