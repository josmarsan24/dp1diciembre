package org.springframework.samples.petclinic.model;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.samples.petclinic.service.exceptions.AddParticipanteSancionadoException;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "athletes")
public class Athlete extends Persona{

	@Column(name = "height") 
	@Pattern(regexp = "[1-2]{1}[.]\\d{2}")
	private String height;
	
	@Column(name = "weight")
	@Min(30)
	@Max(200)
	@NotNull
	private Integer weight;
	
	@Column(name = "genero")
	private Genero genero;
	
	//
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	//
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "athlete")
	private Set<Sancion> sanciones;
	
	protected Set<Sancion> getSancionesInternal(){
		if (this.sanciones == null) {
			this.sanciones = new HashSet<>();
		}
		return this.sanciones;
	}

	protected void setSancionesInternal(Set<Sancion> sanciones) {
		this.sanciones = sanciones;
	}
	

	public void addSancion(Sancion sancion) {
		getSancionesInternal().add(sancion);
		sancion.setAthlete(this);
	}
	
	
	@ManyToOne
	@JoinColumn(name = "entrenador_id")
	private Entrenador entrenador;
	
	@ManyToOne
	@JoinColumn(name = "patrocinador_id")
	private Patrocinador patrocinador;
	
	@ManyToMany(mappedBy = "participantes")
	private Set<Torneo> torneos;
	
	protected Set<Torneo> getTorneosInternal(){
		if (this.torneos == null) {
			this.torneos = new HashSet<>();
		}
		return this.torneos;
	}

	protected void setTorneosInternal(Set<Torneo> torneos) {
		this.torneos = torneos;
	}
	
	public Set<Torneo> getTorneos() {
		return this.getTorneosInternal();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name = "deporte_id")
	private Deporte deporte;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "atleta")
	private Set<Resultado> resultados;
	
	public void addResultado(Resultado resultado) {
		getResultadosInternal().add(resultado);
		resultado.setAtleta(this);
	}
	
	protected Set<Resultado> getResultadosInternal(){
		if (this.resultados == null) {
			this.resultados = new HashSet<>();
		}
		return this.resultados;
	}
}	
	
	
	
	
