package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.samples.petclinic.model.Genero;
import org.springframework.samples.petclinic.model.Sancion;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EntrenadorService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=AthleteController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AthleteControllerTests {
	
	private static final int TEST_ENTRENADOR_ID = 1;
	private static final int TEST_ENTRENADOR_ID2 = 2;
	private static final int TEST_ATHLETE_ID = 1;
	private static final int TEST_ATHLETE_ID2 = 2;
	
	@Autowired
	private AthleteController athleteController;
	
	@MockBean
	private EntrenadorService entrenadorService;
	
	@MockBean
	private AthleteService athleteService;
	
    @MockBean
	private UserService userService;
    
    @MockBean
    private AuthoritiesService authoritiesService; 
    
	@Autowired
	private MockMvc mockMvc;
	
	private Entrenador ent;
	private Athlete ath;
	private Entrenador ent2;
	private Athlete ath2;
	
	@BeforeEach
	void setup() {
		ent = new Entrenador();
		ath = new Athlete();
		ath.setId(TEST_ENTRENADOR_ID);
		ath.setNombre("Nombre");
		ath.setApellidos("Apellidos");
		ath.setWeight(70);
		ath.setHeight("1.77");
		ath.setDni("43251426F");
		ath.setEmail("");
		ath.setGenero(Genero.HOMBRE);
		Set<Athlete> athletes = new HashSet<Athlete>(); 
		athletes.add(ath);
		ent.setId(TEST_ENTRENADOR_ID);
		ent.setNombre("entrenador1");
		ent.setApellidos("lastName");
		ent.setDni("43251426G");
		ent.setEmail("");
		ent.setAthletes(athletes);
		//entrenador y athlete con user
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		User user2 = new User();
		user2.setUsername("username2");
		user2.setPassword("password2");
		ent2 = new Entrenador();
		ath2 = new Athlete();
		ath2.setId(TEST_ENTRENADOR_ID2);
		ath2.setNombre("Nombre");
		ath2.setApellidos("Apellidos");
		ath2.setWeight(70);
		ath2.setHeight("1.77");
		ath2.setDni("43251426F");
		ath2.setEmail("");
		ath2.setGenero(Genero.HOMBRE);
		ath2.setUser(user);
		Set<Athlete> athletes2 = new HashSet<Athlete>(); 
		athletes2.add(ath);
		ent2.setId(TEST_ENTRENADOR_ID2);
		ent2.setNombre("entrenador");
		ent2.setApellidos("lastName");
		ent2.setDni("43251426G");
		ent2.setEmail("");
		ent2.setUser(user2);
		ent2.setAthletes(athletes2);
		given(entrenadorService.findEntrenadorById(TEST_ENTRENADOR_ID)).willReturn(ent);
		given(athleteService.findAthleteByEntrenadorId(TEST_ENTRENADOR_ID)).willReturn(athletes);
		given(athleteService.findAthleteById(TEST_ATHLETE_ID)).willReturn(ath);
		given(entrenadorService.findEntrenadorById(TEST_ENTRENADOR_ID2)).willReturn(ent2);
		given(athleteService.findAthleteByEntrenadorId(TEST_ENTRENADOR_ID2)).willReturn(athletes2);
		given(athleteService.findAthleteById(TEST_ATHLETE_ID2)).willReturn(ath2);
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/athletes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("athlete"))
		.andExpect(view().name("athletes/createOrUpdateAthleteForm"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/athletes/new").param("nombre", "Julian").param("apellidos", "Fernandez").param("dni","43251426B").param("email", "")
				.param("height", "1.80").param("weight", "80")
						.with(csrf()))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/athletes"));
}
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormFailed() throws Exception {
		mockMvc.perform(post("/athletes/new").param("nombre", "Julian").param("apellidos", "Fernandez").param("dni","43251426").param("email", "")
				.param("height", "18.0").param("weight", "80.0")			
				.with(csrf()))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("athletes/createOrUpdateAthleteForm"));
	}
	
	@WithMockUser(value = "spring")
	@ParameterizedTest
	@ValueSource(ints = { TEST_ENTRENADOR_ID,TEST_ENTRENADOR_ID2})
    void testInitCreationAthleteDeEntrenadorForm(int arguments) throws Exception {
		mockMvc.perform(get("/entrenadores/{entrenadorId}/athletes/new",arguments)).andExpect(status().isOk()).andExpect(model().attributeExists("athlete"))
		.andExpect(view().name("athletes/createOrUpdateAthleteForm"));
}
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationAthleteDeEntrenadorFormSuccess() throws Exception {
		mockMvc.perform(post("/entrenadores/{entrenadorId}/athletes/new",TEST_ENTRENADOR_ID)
				.param("nombre", "Julian").param("apellidos", "Fernandez")
				.param("dni","43251426B").param("email", "")
				.param("height", "1.80").param("weight", "80")
						.with(csrf()))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/entrenadores/{entrenadorId}"));
}
	
	@WithMockUser(value = "spring")
    @ParameterizedTest
	@ValueSource(ints = { TEST_ENTRENADOR_ID,TEST_ENTRENADOR_ID2})
	void testProcessCreationAthleteDeEntrenadorFormFailed(int arguments) throws Exception {
		mockMvc.perform(post("/entrenadores/{entrenadorId}/athletes/new",arguments).param("nombre", "Julian")
				.param("apellidos", "Fernandez").param("dni","43251426").param("email", "")
				.param("height", "18.0").param("weight", "80.0")			
				.with(csrf()))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("athletes/createOrUpdateAthleteForm"));
	}
	
	@WithMockUser(value = "spring")
	@ParameterizedTest
	@ValueSource(ints = { TEST_ATHLETE_ID,TEST_ATHLETE_ID2})
    void testInitUpdateAthleteForm(int arguments) throws Exception {
		mockMvc.perform(get("/athletes/show/{athleteId}/edit",arguments)).andExpect(status().isOk())
			.andExpect(model().attributeExists("athlete"))
			.andExpect(model().attribute("athlete", hasProperty("apellidos", is("Apellidos"))))
			.andExpect(model().attribute("athlete", hasProperty("nombre", is("Nombre"))))
			.andExpect(model().attribute("athlete", hasProperty("dni",is("43251426F"))))
			.andExpect(model().attribute("athlete", hasProperty("email",is(""))))
			.andExpect(model().attribute("athlete", hasProperty("height",is("1.77"))))
			.andExpect(model().attribute("athlete", hasProperty("weight",is(70))))
			.andExpect(view().name("athletes/createOrUpdateAthleteForm"));
}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAthleteFormfailed() throws Exception {
		mockMvc.perform(post("/athletes/show/{athleteId}/edit",TEST_ATHLETE_ID)
				.param("nombre", "Nombre").param("apellidos", "Apellidos")
				.param("dni","43251426False").param("email", "")
				.param("height", "1.77").param("weight", "70.7")			
				.with(csrf()))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("athletes/createOrUpdateAthleteForm"));
	}

 
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAthleteFormSuccess() throws Exception {
		mockMvc.perform(post("/athletes/show/{athleteId}/edit",TEST_ATHLETE_ID)
				.param("nombre", "Alfonso").param("apellidos", "Decimo")
				.param("dni","43251426F").param("email", "")
				.param("height", "1.77").param("weight", "70")			
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/athletes/show/{athleteId}"));
	}
	
	@WithMockUser(value = "spring")
	@ParameterizedTest
	@ValueSource(ints = { TEST_ATHLETE_ID,TEST_ATHLETE_ID2})
	void testShowAthlete(int arguments) throws Exception {
		mockMvc.perform(get("/athletes/show/{athleteId}",arguments)).andExpect(status().isOk())
		.andExpect(model().attributeExists("athlete"))
		.andExpect(model().attribute("athlete", hasProperty("apellidos", is("Apellidos"))))
		.andExpect(model().attribute("athlete", hasProperty("nombre", is("Nombre"))))
		.andExpect(model().attribute("athlete", hasProperty("dni",is("43251426F"))))
		.andExpect(model().attribute("athlete", hasProperty("email",is(""))))
		.andExpect(model().attribute("athlete", hasProperty("height",is("1.77"))))
		.andExpect(model().attribute("athlete", hasProperty("weight",is(70))))
		.andExpect(view().name("athletes/athleteDetails"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListAthletes() throws Exception {
		mockMvc.perform(get("/athletes")).andExpect(status().isOk()).andExpect(model().attributeExists("athletes"))
			.andExpect(view().name("athletes/listAthletes"));
}
	
	 @WithMockUser(value = "spring")
	  @Test
	  void testDeleteEntrenador() throws Exception {
		  mockMvc.perform(get("/athletes/delete/{athletesId}", TEST_ATHLETE_ID))
		  .andExpect(view().name("athletes/listAthletes"));
}
}
