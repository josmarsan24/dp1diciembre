package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "entrenadores")
public class Entrenador extends Persona {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "entrenador")
	private Set<Athlete> athletes;
	
	//
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	//
	
	public void addAthlete(Athlete athlete) {

		getAthletesInternal().add(athlete);
		athlete.setEntrenador(this);
	}
	
	protected Set<Athlete> getAthletesInternal() {
		if (this.athletes == null) {
			this.athletes = new HashSet<>();
		}
		return this.athletes;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name = "deporte_id")
	@NotNull
	private Deporte deporte;
}

