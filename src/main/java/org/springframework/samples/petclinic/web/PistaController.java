package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.service.PistaService;
import org.springframework.samples.petclinic.service.TorneoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.sun.tools.sjavac.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/pistas")
public class PistaController {
	
	
	@Autowired
	private PistaService pistaService;
	@Autowired
	private TorneoService torneoService;
	
	@GetMapping()
	public String listadoPistas(ModelMap modelMap) {
		String view="pistas/listPistas";
		Iterable<Pista> pistas = pistaService.findAll();
		modelMap.addAttribute("pistas", pistas);
		log.info("Se accede al listado de pistas");
		return view;
	}
	
	@GetMapping(path="/new")
	public String crearPista(ModelMap modelMap) {
		String view="pistas/editPista";
		modelMap.addAttribute("pista", new Pista());
		log.info("Se va a crear una nueva pista");
		return view;
	}
	
	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Pista pista, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("pista", pista);
			log.error("Hay errores en el modelo");
			return "pistas/editPista";
		} else {
			this.pistaService.save(pista);
			log.info("Se ha creado la pista con nombre "+pista.getName());
			return "redirect:/pistas";
			}
		}

	@GetMapping(path="/delete/{pistaId}")
	public String borrarPista(@PathVariable("pistaId") int pistaId, ModelMap modelMap) {
		String view="pistas/listPistas";
		Pista pista = pistaService.findPistaById(pistaId);
		
			torneoService.borrarPistaTorneos(pista);
			pistaService.delete(pista);
			modelMap.addAttribute("message", "Pista borrada con exito");
			view = listadoPistas(modelMap);
			log.info("Se ha eliminado la pista "+ pista.getName());
			
		return view;
	}
}
