package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    
		  log.info("Se ha iniciado la aplicación");
		  List<Person> personas = new ArrayList<Person>();
		  
		  Person jualeoval= new Person();
		  jualeoval.setFirstName("Juan Jose");
		  jualeoval.setLastName("León Valderrama");
		  personas.add(jualeoval);
		  
		  Person josmarsan= new Person();
		  josmarsan.setFirstName("José");
		  josmarsan.setLastName("Martín Sanchez");
		  personas.add(josmarsan);
		  
		  Person fersilleo= new Person();
		  fersilleo.setFirstName("Fernando");
		  fersilleo.setLastName("Silva León");
		  personas.add(fersilleo);
		  
		  model.put("personas", personas);
		  model.put("title", "Olimpicks");
		  model.put("group", "Grupo 1 Diciembre");
	    return "welcome";
	  }
}
