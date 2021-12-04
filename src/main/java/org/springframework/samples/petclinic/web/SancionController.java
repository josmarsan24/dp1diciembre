package org.springframework.samples.petclinic.web;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Sancion;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.SancionService;
import org.springframework.samples.petclinic.service.exceptions.IncongruentSancionDateExcepcion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jdk.internal.jline.internal.Log;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/athletes/show/{athleteId}")
public class SancionController {
	private final AthleteService athleteService;
	private final SancionService sancionService;

	@Autowired
	public SancionController(AthleteService athleteService, SancionService sancionService) {
		this.athleteService = athleteService;
		this.sancionService =sancionService;
	}
	
	
	@ModelAttribute("athlete")
	public Athlete findAthlete(@PathVariable("athleteId") int athleteId) {
		return this.athleteService.findAthleteById(athleteId);
	}
	
	@GetMapping(path="/newSancion")
	public String sancionarDeportista(@PathVariable("athleteId")  int athleteId,ModelMap model) {
		Athlete athlete = athleteService.findAthleteById(athleteId);
		Sancion sancion = new Sancion();
		sancion.setAthlete(athlete);
		model.put("sancion", sancion);
		log.info("El deportista "+athlete.getNombre()+" "+athlete.getApellidos()+ "está sancionado por el siguiente motivo: "+sancion.getDescripcion());
		return "athletes/createOrUpdateSancionForm";
	}
	
	@PostMapping(value = "/newSancion")
	public String processNewSancionForm(@PathVariable("athleteId")  int athleteId, 
			@Valid Sancion sancion, BindingResult result,ModelMap model) {
		if (result.hasErrors()) {
			sancion.setAthlete(athleteService.findAthleteById(athleteId));
			model.put("sancion", sancion);
			log.error("Hay errores en el formulario");
			return "athletes/createOrUpdateSancionForm";
		}
		else {
			try {
			sancion.setAthlete(athleteService.findAthleteById(athleteId));
			this.sancionService.saveSancion(sancion);
			}catch (IncongruentSancionDateExcepcion ex) {
				result.rejectValue("fechaFin", "fechaPosActual", "Debe ser posterior al dia actual");
				log.error("La fecha debe ser posterior a la fecha actual");
				return "athletes/createOrUpdateSancionForm";
			}
			log.info("Se ha creado la sanción");
			return "redirect:/athletes/show/{athleteId}";
		}
	}
	
	@GetMapping(path = "/delete/{sancionId}")
	public String eliminarSanción(@PathVariable("athleteId") int athleteId,
			@PathVariable("sancionId") int sancionId, ModelMap modelMap) {
		Sancion sancion = this.sancionService.findSancionById(sancionId);
		this.sancionService.deleteSancion(sancion);
		log.info("Se ha eliminado la sanción");
		return "redirect:/athletes/show/{athleteId}";
		
	}
	
}
