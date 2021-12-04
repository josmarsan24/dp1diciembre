package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EntrenadorService;
import org.springframework.samples.petclinic.service.TorneoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.IncongruentTorneoFinDateExcepcion;
import org.springframework.samples.petclinic.service.exceptions.IncongruentTorneoIniDateExcepcion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class TorneoController {

	private static final String VIEWS_TORNEO = "torneos/editTorneos";

	private TorneoService torneoService;
	private AthleteService athleteService;
	
	@Autowired
	public TorneoController(AthleteService athleteService, TorneoService torneoService) {
		this.athleteService = athleteService;
        this.torneoService = torneoService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("deportes")
	public Collection<Deporte> getDeporte() {
		return this.athleteService.findDeporteTypes();
	}

	@ModelAttribute("pistas")
	public Collection<Pista> getPista() {
		return this.torneoService.findPistaTypes();
	}
	
	@GetMapping("/torneos")
	public String listadoTorneos(ModelMap modelMap) {
		String view = "torneos/listTorneos";
		Iterable<Torneo> torneos = torneoService.findAll();
		modelMap.addAttribute("torneos", torneos);
		log.info("Se accede a la lista de todos los torneos");
		return view;
	}

	@GetMapping(path = "/torneos/new")
	public String crearTorneo(ModelMap modelMap) {
		String view = "torneos/editTorneos";
		modelMap.addAttribute("torneo", new Torneo());
		log.info("Se va a crear un nuevo torneo");
		return view;
	}

	@PostMapping(path = "/torneos/new")
	public String salvarTorneo(@Valid Torneo torneo, BindingResult result, ModelMap modelMap) {
		String view = "torneos/listTorneos";
		if (result.hasErrors()) {
			modelMap.addAttribute("torneo", torneo);
			log.error("Hay errores en el formulario");
			return "torneos/editTorneos";
		} else {
			try {
				torneoService.save(torneo);
			} catch (IncongruentTorneoIniDateExcepcion e) {
				result.rejectValue("fechaInicio", "torneoDateIni", "La fecha de inicio debe ser posterior al día actual");
				log.error("No se ha creado el torneo. La fecha de inicio debe ser posterior a la actual");
				return "torneos/editTorneos";
			} catch (IncongruentTorneoFinDateExcepcion e) {
				result.rejectValue("fechaFin", "torneoDateFin", "La fecha de fin debe ser posterior al la de inicio");
				log.error("No se ha creado el torneo. La fecha de fin debe ser posterior a la de inicio");
				return "torneos/editTorneos";
			}
			modelMap.addAttribute("message", "Torneo guardado con exito");
			log.info("Se ha creado el torneo "+torneo.getName());
			view = listadoTorneos(modelMap);
		}

		return view;
	}

	@GetMapping(path = "/torneos/delete/{torneoId}")
	public String borrarTorneo(@PathVariable("torneoId") int torneoId, ModelMap modelMap) {
		String view = "torneos/listTorneos";
		Torneo torneo = torneoService.findTorneoById(torneoId);
			torneoService.delete(torneo);
			modelMap.addAttribute("message", "Torneo borrado con exito");
			log.info("Se ha eliminado el torneo");
			view = listadoTorneos(modelMap);
		return view;
	}

	@GetMapping(value = "/torneos/edit/{torneoId}")
	public String editarTorneo(@PathVariable("torneoId") int torneoId, ModelMap model) {
		Torneo torneo = this.torneoService.findTorneoById(torneoId);
		boolean edit = true;
		model.addAttribute("torneo", torneo);
		model.addAttribute("edit", edit);
		log.info("Se va a editar el torneo "+torneo.getName());
		return "torneos/editTorneos";
	}
	
	@PostMapping(value = "/torneos/edit/{torneoId}")
	public String processUpdateTorneoForm(@Valid Torneo torneo, BindingResult result,
			@PathVariable("torneoId") int torneoId, ModelMap model) {
		if (result.hasErrors()) {
			boolean edit = true;
			model.put("edit", edit);
			log.warn("Hay errores en el formulario");
			return VIEWS_TORNEO;
		} else {
			Torneo torneoAc = this.torneoService.findTorneoById(torneoId);
			torneoAc.setName(torneo.getName());
			torneoAc.setPista(torneo.getPista());
			torneoAc.setFechaInicio(torneo.getFechaInicio());
			torneoAc.setFechaFin(torneo.getFechaFin());
			try { 
				torneoService.save(torneoAc);
			} catch (IncongruentTorneoIniDateExcepcion e) {
				result.rejectValue("fechaInicio", "torneoDateIni", "La fecha de inicio debe ser posterior al día actual");
				log.error("No se ha creado el torneo. La fecha de inicio debe ser posterior a la actual");
				return "torneos/editTorneos";
			} catch (IncongruentTorneoFinDateExcepcion e) {
				result.rejectValue("fechaFin", "torneoDateFin", "La fecha de fin debe ser posterior al la de inicio");
				log.error("No se ha creado el torneo. La fecha de fin debe ser posterior a la de inicio");
				return "torneos/editTorneos";
			}
			log.info("Se ha editado el torneo");
			return "redirect:/torneos";
		}

	}
	
	
	@GetMapping(path = "/torneos/show/{torneoId}/add")
	public String buscarParticipante(@PathVariable("torneoId") int torneoId, ModelMap modelMap) {
		Torneo torneo = this.torneoService.findTorneoById(torneoId);
		if(torneo.getFechaFin().isBefore(LocalDate.now())) {
			modelMap.addAttribute("message", "No se pueden añadir participantes, el torneo esta en curso o ya termino");
			log.error("No se pueden añadir participantes, el torneo esta en curso o ya termino");
			String view = showTorneo(torneoId,modelMap);
			return view;
		}
		Set <Athlete> participantes = this.torneoService.buscarParticipantes(torneo);
		modelMap.addAttribute("participantes", participantes);
		modelMap.addAttribute("torneo", torneo);
		String vista = "torneos/addParticipantes";
		return vista;
		
	}
	
	@GetMapping(path = "/torneos/show/{torneoId}/add/{athleteId}")
	public String añadirParticipante(@PathVariable("torneoId") int torneoId, @PathVariable("athleteId") int athleteId, ModelMap modelMap) {
		Athlete atleta = this.athleteService.findAthleteById(athleteId);
		Torneo torneo = this.torneoService.findTorneoById(torneoId);
		torneo.addParticipante(atleta);
		try {
			this.torneoService.save(torneo);
		} catch (IncongruentTorneoIniDateExcepcion | IncongruentTorneoFinDateExcepcion e) {
		}
		return "redirect:/torneos/show/{torneoId}";
	}
	
	@GetMapping("/torneos/show/{torneoId}")
	public String showTorneo(@PathVariable("torneoId") int torneoId,ModelMap model) {
		Torneo torneo = this.torneoService.findTorneoById(torneoId);
		List<Athlete> atheltesWithoutResult = torneo.getParticipantes().stream().filter(x->!x.getResultados().stream().anyMatch(y->y.getTorneo()==torneo)).collect(Collectors.toList());
		model.addAttribute("torneo",torneo);
		model.addAttribute("atheltesWithoutResult", atheltesWithoutResult);
		log.info("Mostrando los datos del torneo "+ torneo.getName());
		return "torneos/torneoDetails";
	}

}
