package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;




@MappedSuperclass
public class Persona extends BaseEntity{
	
	@Column(name = "nombre")
	@NotEmpty
	private String nombre;
	
	@Column(name = "apellidos")
	@NotEmpty
	private String apellidos;
	
	@Column(name = "dni")
	@Pattern(regexp = "\\d{8}[A-Z]{1}")
	private String dni;
	
	@Column(name = "email")
	@Pattern(regexp = "([^@]+{1,60}@((hotmail)|(gmail))(([.]com)|([.]es)))|")
	private String email;
	
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	
	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
