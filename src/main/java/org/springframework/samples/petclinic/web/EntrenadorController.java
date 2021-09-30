package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.jboss.jandex.TypeTarget.Usage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EntrenadorService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EntrenadorController {

	private static final String VIEWS_ENTRENADOR_CREATE_OR_UPDATE_FORM = "entrenadores/createOrUpdateEntrenadorForm";

	private final EntrenadorService entrenadorService;
	private final AthleteService athleteService;

	@Autowired
	public EntrenadorController(EntrenadorService entrenadorService, AthleteService athleteService,
			UserService userService) {
		this.entrenadorService = entrenadorService;
		this.athleteService = athleteService;
	}
	
	@ModelAttribute("deportes")
	public Collection<Deporte> getDeporte(){
		return this.athleteService.findDeporteTypes();
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/entrenador/new")
	public String initCreationForm(Map<String, Object> model) {
		Entrenador entrenador = new Entrenador();
		model.put("entrenador", entrenador);
		return VIEWS_ENTRENADOR_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/entrenador/new")
	public String processCreationForm(@Valid Entrenador entrenador, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_ENTRENADOR_CREATE_OR_UPDATE_FORM;
		} else {
			this.entrenadorService.save(entrenador);

			return "redirect:/entrenadores/" + entrenador.getId();
		}
	}

	@GetMapping("/entrenadores")
	public String entrenadoresList(ModelMap modelMap) {
		String vista = "entrenadores/entrenadorList";
		Iterable<Entrenador> entrenadores = entrenadorService.findAll();
		modelMap.addAttribute("entrenadores", entrenadores);
		return vista;
	}

	@GetMapping(value = "/entrenadores/{entrenadorId}/edit")
	public String initUpdateOwnerForm(@PathVariable("entrenadorId") int entrenadorId, ModelMap model) {
		Entrenador entrenador = this.entrenadorService.findEntrenadorById(entrenadorId);
		model.addAttribute(entrenador);
		if(entrenador.getUser()!=null) {
			model.addAttribute("username",entrenador.getUser().getUsername());
		}
		boolean edit = true;
		model.put("edit", edit);
		return VIEWS_ENTRENADOR_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/entrenadores/{entrenadorId}/edit")
	public String processUpdateOwnerForm(@Valid Entrenador entrenador, BindingResult result,
			@PathVariable("entrenadorId") int entrenadorId,ModelMap model) {
		if (result.hasErrors()) {
			boolean edit = true;
			model.put("edit", edit);
			return VIEWS_ENTRENADOR_CREATE_OR_UPDATE_FORM;
		} else {
			entrenador.setId(entrenadorId);
			entrenador.setUser(this.entrenadorService.findEntrenadorById(entrenadorId).getUser());
			entrenador.setDeporte(this.entrenadorService.findEntrenadorById(entrenadorId).getDeporte());
			this.entrenadorService.save(entrenador);
			return "redirect:/entrenadores/{entrenadorId}";
		}
	}

	@GetMapping("/entrenadores/{entrenadorId}")
	public String showEntrenador(@PathVariable("entrenadorId") int entrenadorId,ModelMap model) {
		Entrenador entrenador = this.entrenadorService.findEntrenadorById(entrenadorId);
		if(entrenador.getUser()!=null) {
			model.addAttribute("username",entrenador.getUser().getUsername());
		}
		model.addAttribute("entrenador",entrenador);
		return "entrenadores/entrenadorDetails";
	}

	@GetMapping(path = "/entrenadores/{entrenadorId}/delete/{athleteId}/{user}")
	public String eliminarEntrenado(@PathVariable("entrenadorId") int entrenadorId, @PathVariable("athleteId") int athleteId,@PathVariable("user") String user, ModelMap modelMap) {
		String usuario="";
		if(this.entrenadorService.findEntrenadorById(entrenadorId).getUser()!=null) {
			usuario = this.entrenadorService.findEntrenadorById(entrenadorId).getUser().getUsername();
		}
		if(user.equals("gy7gt87qgwowhbudvhbwkwpfk4fa545w46894wdyftwqtfvdghwvdywt76twt7tqte")||user.equals(usuario)) {
		this.athleteService.eliminarEntrenadorDeAtleta(athleteId);
		return "redirect:/entrenadores/{entrenadorId}";
		}
		return "redirect:/";
	}
	
	@GetMapping(path = "/entrenadores/{entrenadorId}/add")
	public String buscarEntrenado(@PathVariable("entrenadorId") int entrenadorId, ModelMap modelMap) {
		Entrenador entrenador = this.entrenadorService.findEntrenadorById(entrenadorId);
		Set <Athlete> atletas = this.athleteService.buscarAtletaConMiDeporteSinEntrenador(entrenador.getDeporte());
		if(entrenador.getUser()!=null) {
			modelMap.addAttribute("username",entrenador.getUser().getUsername());
		}
		modelMap.addAttribute("atletas", atletas);
		modelMap.addAttribute("entrenador", entrenador);
		String vista = "entrenadores/entrenados";
		return vista;
		
	}
	
	@GetMapping(path = "/entrenadores/{entrenadorId}/add/{athleteId}")
	public String añadirEntrenado(@PathVariable("entrenadorId") int entrenadorId, @PathVariable("athleteId") int athleteId, ModelMap modelMap) {
		this.athleteService.añadirEntrenadorDeAtleta(athleteId,entrenadorId);
		return "redirect:/entrenadores/{entrenadorId}";
	}
	
	@GetMapping(path="/entrenadores/delete/{entrenadorId}")
	public String borrarEntrenador(@PathVariable("entrenadorId") int entrenadorId, ModelMap modelMap) {
		Entrenador entrenador = this.entrenadorService.findEntrenadorById(entrenadorId);
		this.entrenadorService.delete(entrenador);
		modelMap.addAttribute("message", "Entrenador borrado con exito");
		String view = entrenadoresList(modelMap);
		return view;
	}

}
