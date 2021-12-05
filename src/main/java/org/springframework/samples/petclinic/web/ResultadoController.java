package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Resultado;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.ResultadoService;
import org.springframework.samples.petclinic.service.TorneoService;
import org.springframework.samples.petclinic.service.exceptions.IncrongruentPositionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class ResultadoController {

	@Autowired
	private ResultadoService resultadoService;
	@Autowired
	private AthleteService athleteService;
	@Autowired
	private TorneoService torneoService;

	@GetMapping(path = "/torneos/show/{torneoId}/{athleteId}/resultado/new")
	public String crearResultado(@PathVariable int torneoId, @PathVariable int athleteId, ModelMap modelMap) {
		String view = "resultados/editResultado";
		Torneo torneo = torneoService.findTorneoById(torneoId);
		Athlete athlete = athleteService.findAthleteById(athleteId);
		Resultado resultado = new Resultado();
		resultado.setTorneo(torneo);
		resultado.setAtleta(athlete);
		modelMap.addAttribute("resultado", resultado);
		modelMap.addAttribute("torneo", torneo);
		modelMap.addAttribute("athlete", athlete);
		return view;
	}

	@PostMapping(value = "/torneos/show/{torneoId}/{athleteId}/resultado/new")
	public String processCreationForm(@PathVariable int torneoId, @PathVariable int athleteId,
			@Valid final Resultado resultado, final BindingResult result, final ModelMap model)
			throws DataAccessException, IncrongruentPositionException {
		Athlete atleta = athleteService.findAthleteById(athleteId);
		Torneo torneo = torneoService.findTorneoById(torneoId);
		if (result.hasErrors()) {
			resultado.setAtleta(atleta);
			resultado.setTorneo(torneo);
			model.put("resultado", resultado);
			model.addAttribute("torneo", torneo);
			model.addAttribute("athlete", atleta);
			log.error("Hay errores en el formulario");
			return "resultados/editResultado";
		} else {
			try {
				resultado.setAtleta(atleta);
				resultado.setTorneo(torneo);
				this.resultadoService.save(resultado);
			} catch (IncrongruentPositionException ex) {
				result.rejectValue("posicion", "invalidPosition",
						"la posicion esta fuera de rango o ya esta ocupada por otro atleta");
				model.addAttribute("torneo", torneo);
				model.addAttribute("athlete", atleta);
				log.error("La posicion esta fuera de rango o ya esta ocupada por otro atleta");
				return "resultados/editResultado";
			}
			log.info("Se ha creado el resultado del torneo "+ torneo.getName());
			return "redirect:/torneos/show/{torneoId}";
		}
	}
}
