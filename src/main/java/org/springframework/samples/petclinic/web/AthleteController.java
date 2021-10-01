package org.springframework.samples.petclinic.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.PetclinicApplication;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EntrenadorService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class AthleteController {
	
	private AthleteService athleteService;
	private EntrenadorService entrenadorService;
	
	@Autowired
	public AthleteController(AthleteService athleteService, EntrenadorService entrenadorService,UserService userService, AuthoritiesService authoritiesService) {
		this.athleteService = athleteService;
        this.entrenadorService = entrenadorService;
	}
	
	private static final String VIEWS_ATHLETE_CREATE_OR_UPDATE_FORM = "athletes/createOrUpdateAthleteForm";
	
	
	@ModelAttribute("genero")
	public Collection<String> getGenero(){
		Collection<String> genero = Arrays.asList("HOMBRE","MUJER");
		return genero;
	}
	
	@ModelAttribute("deportes")
	public Collection<Deporte> getDeporte(){
		return this.athleteService.findDeporteTypes();
	}
	
	@GetMapping( value = "/entrenadores/{entrenadorId}/athletes/new")
	public String initCreationForm(@PathVariable("entrenadorId") int entrenadorId,Entrenador entrenador, ModelMap model) {
		Athlete athlete = new Athlete();
		entrenador.addAthlete(athlete);
		if(this.entrenadorService.findEntrenadorById(entrenadorId).getUser()!=null) {
			model.addAttribute("username",this.entrenadorService.findEntrenadorById(entrenadorId).getUser().getUsername());
		}
		model.put("athlete", athlete);
		model.put("edit", true);
		return VIEWS_ATHLETE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/entrenadores/{entrenadorId}/athletes/new")
	public String processCreationForm(@PathVariable("entrenadorId") int entrenadorId, @Valid final Athlete athlete, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("athlete", athlete);
			if(this.entrenadorService.findEntrenadorById(entrenadorId).getUser()!=null) {
				model.addAttribute("username",this.entrenadorService.findEntrenadorById(entrenadorId).getUser().getUsername());
			}
			log.warn("Hay errores en el formulario");
			return AthleteController.VIEWS_ATHLETE_CREATE_OR_UPDATE_FORM;
		} else {
			Entrenador entrenador = this.entrenadorService.findEntrenadorById(entrenadorId);
			entrenador.addAthlete(athlete);
			athlete.setDeporte(entrenador.getDeporte());
			this.athleteService.save(athlete);
			log.info("Se ha creado el deportista "+athlete.getNombre() + " " + athlete.getApellidos());
			this.entrenadorService.save(entrenador);
			log.info("Se ha añadido el deportista al entrenador "+entrenador.getNombre() + " " + entrenador.getApellidos());
			return "redirect:/entrenadores/{entrenadorId}";
			}
		}
	
	@GetMapping(value= "/athletes/show/{athleteId}/edit")
	public String initUpdateForm(@PathVariable("athleteId") int athleteId, ModelMap model) {
		Athlete athlete = this.athleteService.findAthleteById(athleteId);
		boolean edit = true;
		model.put("athlete", athlete);
		model.put("edit", edit);
		if(athlete.getUser()!=null) {
			model.addAttribute("username",athlete.getUser().getUsername());
		}
		return VIEWS_ATHLETE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/athletes/show/{athleteId}/edit")
	public String processUpdateForm(@Valid Athlete athlete, BindingResult result,@PathVariable("athleteId") int athleteId, ModelMap model) {		
		boolean edit = true;
		if(result.hasErrors()) {
			model.put("athlete", athlete);
			model.put("edit", edit);
			log.warn("Hay errores en el formulario");
			return VIEWS_ATHLETE_CREATE_OR_UPDATE_FORM;
		} else {
			Athlete athleteToUpdate=this.athleteService.findAthleteById(athleteId);
			athlete.setUser(athleteToUpdate.getUser());
			athlete.setDeporte(athleteToUpdate.getDeporte());
			BeanUtils.copyProperties(athlete, athleteToUpdate, "id","entrenador");
			this.athleteService.save(athleteToUpdate); 
			log.info("Se ha actualizado el deportista "+athlete.getNombre()+" "+athlete.getApellidos());
			return "redirect:/athletes/show/{athleteId}";
		}
		
	}
	
	@GetMapping( value = "/athletes/new")
	public String initCreationForm(ModelMap model) {
		Athlete athlete = new Athlete();
		model.put("athlete", athlete);
		return VIEWS_ATHLETE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/athletes/new")
	public String processCreationForm(@Valid final Athlete athlete, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("athlete", athlete);
			log.warn("Hay errores en el formulario");
			return AthleteController.VIEWS_ATHLETE_CREATE_OR_UPDATE_FORM;
		} else {
			this.athleteService.save(athlete);
			log.info("Se ha creado el deportista "+athlete.getNombre() + " " + athlete.getApellidos());
			return "redirect:/athletes";
			}
		}
	@GetMapping("/athletes")
	public String athletesList(ModelMap modelMap) {
		String vista = "athletes/listAthletes";
		Iterable<Athlete> athletes = this.athleteService.findAll();
		modelMap.addAttribute("athletes", athletes);
		log.info("Se accede a la lista de deportistas");
		return vista;
		
	}
	
	@GetMapping(value = "/athletes/show/{athleteId}")
	public String showAthlete(@PathVariable("athleteId") int athleteId, ModelMap modelMap) {
		Athlete athlete = this.athleteService.findAthleteById(athleteId);
		if(athlete.getUser()!=null) {
			modelMap.addAttribute("username",athlete.getUser().getUsername());
		}
		modelMap.addAttribute("athlete",athlete);
		log.info("Se accede a los detalles del deportista "+athlete.getNombre() + " "+athlete.getApellidos());
		return "athletes/athleteDetails";
	}

	@GetMapping(path="/athletes/delete/{athleteId}")
	public String borrarAthlete(@PathVariable("athleteId") int athleteId, ModelMap modelMap) {
		Athlete athlete = athleteService.findAthleteById(athleteId);
		athleteService.delete(athlete);
		modelMap.addAttribute("message", "Deportista borrado con exito");
		String view = athletesList(modelMap);
		return view;
	}
}
	