package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sanciones")
public class Sancion extends BaseEntity{
	
	@Column(name = "fecha_fin") 
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaFin;
	
	@NotBlank
	@Column(name = "descripcion") 
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name = "athlete_id")
	private Athlete athlete;
	
	
}
