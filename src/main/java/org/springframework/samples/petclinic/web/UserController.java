/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EntrenadorService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.exceptions.NotValidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.NotValidUsernameException;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

	private static final String VIEWS_DEPORTISTA_CREATE_FORM = "users/registarNuevoDeportista";
	private static final String VIEWS_ENTRENADOR_CREATE_FORM = "users/registrarNuevoEntrenador";

	
	private final AthleteService athleteService;
	private final EntrenadorService entrenadorService;
	private final UserService userService;
	
	@Autowired
	public UserController(AthleteService deportistaServicio,EntrenadorService entrenadorServicio,UserService userService) {
		this.athleteService = deportistaServicio;
		this.entrenadorService = entrenadorServicio;
		this.userService = userService;
	}
	
	@ModelAttribute("genero")
	public Collection<String> getGenero(){
		Collection<String> genero = Arrays.asList("HOMBRE","MUJER");
		return genero;
	}
	
	@ModelAttribute("deportes")
	public Collection<Deporte> getDeporte(){
		return this.athleteService.findDeporteTypes();
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	
	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		return "users/tipo";
	}
	
	@GetMapping(value = "/users/newDeportista")
	public String initCreationFormDeportista(Map<String, Object> model) {
		Athlete a = new Athlete();
		model.put("athlete", a);
		return VIEWS_DEPORTISTA_CREATE_FORM;
	}
	@GetMapping(value = "/users/newEntrenador")
	public String initCreationFormEntrenador(Map<String, Object> model) {
		Entrenador e = new Entrenador();
		model.put("entrenador", e);
		return VIEWS_ENTRENADOR_CREATE_FORM;
	}


	@PostMapping(value = "/users/newDeportista")
	public String processCreationForm(@Valid Athlete a, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_DEPORTISTA_CREATE_FORM;
		}
		else {
			//creating owner, user, and authority
			try {
				this.userService.validUser(a.getUser());
			} catch (NotValidUsernameException e) {
				result.rejectValue("user.username", "notValidUser", "El usuario no se puede dejar en blanco y no puede estar en uso");
				return VIEWS_DEPORTISTA_CREATE_FORM;
			} catch (NotValidPasswordException e) {
				result.rejectValue("user.password", "notValidPassword", "La contraseña no se puede dejar en blanco");
				return VIEWS_DEPORTISTA_CREATE_FORM;
			}
			this.athleteService.saveUser(a);
			return "redirect:/";
		}
	}


	@PostMapping(value = "/users/newEntrenador")
	public String processCreationForm(@Valid Entrenador e, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_ENTRENADOR_CREATE_FORM;
		}
		else {
			//creating owner, user, and authority
			try {
				this.userService.validUser(e.getUser());
			} catch (NotValidUsernameException t) {
				result.rejectValue("user.username", "notValidUser", "El usuario no se puede dejar en blanco y no puede estar en uso");
				return VIEWS_ENTRENADOR_CREATE_FORM;
			} catch (NotValidPasswordException t) {
				result.rejectValue("user.password", "notValidPassword", "La contraseña no se puede dejar en blanco");
				return VIEWS_ENTRENADOR_CREATE_FORM;
			}
			this.entrenadorService.saveUser(e);
			return "redirect:/";
		}
	}

}
