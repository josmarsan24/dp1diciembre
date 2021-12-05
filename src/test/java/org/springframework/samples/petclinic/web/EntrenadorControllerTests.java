package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Deporte;
import org.springframework.samples.petclinic.model.Entrenador;
import org.springframework.samples.petclinic.model.User;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EntrenadorService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(controllers=EntrenadorController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class EntrenadorControllerTests {

	private static final int TEST_ENTRENADOR_ID = 1;
	private static final int TEST_ATHLETE_ID = 1;
	private static final int TEST_ATHLETE2_ID = 2;
	private static final int TEST_ENTRENADOR_ID2 = 2;
	private static final String adminCode = "gy7gt87qgwowhbudvhbwkwpfk4fa545w46894wdyftwqtfvdghwvdywt76twt7tqte";
	
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
	private Entrenador ent2;
	
	private Athlete ath;
	private Athlete ath2;

	private Deporte deporte;
	@BeforeEach
	void setup() {
		ent = new Entrenador();
		ent2 = new Entrenador();
		ath = new Athlete();
		ath2 = new Athlete();
		deporte = new Deporte();
		deporte.setName("baloncesto");
		Set<Athlete> athletes = new HashSet<Athlete>(); 
		Set<Athlete> athletes2 = new HashSet<Athlete>(); 
		athletes.add(ath);
		athletes2.add(ath2);
		ath.setNombre("ath1");
		ath2.setNombre("ath2");
		ent.setId(TEST_ENTRENADOR_ID);
		ent.setNombre("entrenador1");
		ent.setApellidos("lastName");
		ent.setDni("43251426G");
		ent.setEmail("");
		ent.setAthletes(athletes);
		ent.setUser(null);
		ent.setDeporte(deporte);
		//entrenador con user
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		ent2.setId(TEST_ENTRENADOR_ID2);
		ent2.setNombre("entrenador1");
		ent2.setApellidos("lastName");
		ent2.setDni("43251426G");
		ent2.setEmail("");
		ent2.setUser(user);
		ent2.setDeporte(deporte);
		given(entrenadorService.findEntrenadorById(TEST_ENTRENADOR_ID)).willReturn(ent);
		given(entrenadorService.findEntrenadorById(TEST_ENTRENADOR_ID2)).willReturn(ent2);
		given(athleteService.findAthleteByEntrenadorId(TEST_ENTRENADOR_ID)).willReturn(athletes);
		given(athleteService.buscarAtletaSinEntrenador()).willReturn(athletes2);
		given(athleteService.findAthleteById(TEST_ATHLETE2_ID)).willReturn(ath2);
	
	}
	
	@WithMockUser(value = "spring")
    @Test
void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/entrenador/new")).andExpect(status().isOk()).andExpect(model().attributeExists("entrenador"))
			.andExpect(view().name("entrenadores/createOrUpdateEntrenadorForm"));
}
	
	@WithMockUser(value = "spring")
    @Test
void testProcessCreationFormSuccess() throws Exception {
	mockMvc.perform(post("/entrenador/new").param("nombre", "Julian").param("apellidos", "Fernandez").param("dni","43251426B").param("email", "")
						.with(csrf()))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/entrenadores/null"));
}
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormFailed() throws Exception {
		mockMvc.perform(post("/entrenador/new").param("nombre", "Julian").param("apellidos", "Fernandez").param("dni","43251426").param("email", "")
							.with(csrf()))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("entrenadores/createOrUpdateEntrenadorForm"));
	}
	
	@WithMockUser(value = "spring")
	@ParameterizedTest
	@ValueSource(ints = { TEST_ENTRENADOR_ID,TEST_ENTRENADOR_ID2})
void testInitUpdateEntrenadorForm(int arguments) throws Exception {
	mockMvc.perform(get("/entrenadores/{entrenadorId}/edit",arguments)).andExpect(status().isOk())
			.andExpect(model().attributeExists("entrenador"))
			.andExpect(model().attribute("entrenador", hasProperty("apellidos", is("lastName"))))
			.andExpect(model().attribute("entrenador", hasProperty("nombre", is("entrenador1"))))
			.andExpect(model().attribute("entrenador", hasProperty("dni",is("43251426G"))))
			.andExpect(model().attribute("entrenador", hasProperty("email",is(""))))
			.andExpect(view().name("entrenadores/createOrUpdateEntrenadorForm"));
}
	
	@WithMockUser(value = "spring")
    @Test
void testListEntrenadores() throws Exception {
	mockMvc.perform(get("/entrenadores")).andExpect(status().isOk()).andExpect(model().attributeExists("entrenadores"))
			.andExpect(view().name("entrenadores/entrenadorList"));
}
	
	 @WithMockUser(value = "spring")
		@Test
		void testProcessUpdateFormfailed() throws Exception {
			mockMvc.perform(post("/entrenadores/{entrenadorId}/edit", TEST_ENTRENADOR_ID)
								.with(csrf())
								.param("nombre", "luis")
								.param("apellidos", "chacon")
								.param("dni","123456789S"))
					.andExpect(status().is2xxSuccessful())
					.andExpect(view().name("entrenadores/createOrUpdateEntrenadorForm"));
		}

	 
	@WithMockUser(value = "spring")
		@Test
		void testProcessUpdateFormSuccess() throws Exception {
			mockMvc.perform(post("/entrenadores/{entrenadorId}/edit", TEST_ENTRENADOR_ID)
								.with(csrf())
								.param("nombre", "luis")
								.param("apellidos", "chacon"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/entrenadores/{entrenadorId}"));
		}
	  

	  @WithMockUser(value = "spring")
	  @ParameterizedTest
		@ValueSource(ints = { TEST_ENTRENADOR_ID,TEST_ENTRENADOR_ID2})
		void testShowEntrenador(int arguments) throws Exception {
			mockMvc.perform(get("/entrenadores/{entrenadorId}", arguments)).andExpect(status().isOk())
					.andExpect(model().attribute("entrenador", hasProperty("apellidos", is("lastName"))))
					.andExpect(model().attribute("entrenador", hasProperty("nombre", is("entrenador1"))))
					.andExpect(model().attribute("entrenador", hasProperty("dni",is("43251426G"))))
					.andExpect(model().attribute("entrenador", hasProperty("email",is(""))))
					.andExpect(view().name("entrenadores/entrenadorDetails"));
		}
	  
	  @WithMockUser(value = "spring")
		@Test
		void testDeleteEntrenado() throws Exception {
			mockMvc.perform(get("/entrenadores/{entrenadorId}/delete/{athleteId}/{user}", TEST_ENTRENADOR_ID, TEST_ATHLETE_ID,adminCode))
			.andExpect(view().name("redirect:/entrenadores/{entrenadorId}"));
		}
	  
	  @WithMockUser(value = "spring")
		@Test
		void testUserDeleteEntrenado() throws Exception {
			mockMvc.perform(get("/entrenadores/{entrenadorId}/delete/{athleteId}/{user}", TEST_ENTRENADOR_ID2, TEST_ATHLETE_ID,"username"))
			.andExpect(view().name("redirect:/entrenadores/{entrenadorId}"));
		}

	  @WithMockUser(value = "spring")
		@Test
		void testUserDeleteEntrenadoFailed() throws Exception {
			mockMvc.perform(get("/entrenadores/{entrenadorId}/delete/{athleteId}/{user}", TEST_ENTRENADOR_ID2, TEST_ATHLETE_ID,"username2"))
			.andExpect(view().name("redirect:/"));
		}
	  
	  @WithMockUser(value = "spring")
	  @ParameterizedTest
		@ValueSource(ints = { TEST_ENTRENADOR_ID,TEST_ENTRENADOR_ID2})
		void testBuscarEntrenado(int arguments) throws Exception {
			mockMvc.perform(get("/entrenadores/{entrenadorId}/add", arguments))
			.andExpect(model().attributeExists("atletas"))
			.andExpect(model().attributeExists("entrenador"))
			.andExpect(view().name("entrenadores/entrenados"));
		}
	  
	  @WithMockUser(value = "spring")
		@Test
		void testAñadirEntrenado() throws Exception {
			mockMvc.perform(get("/entrenadores/{entrenadorId}/add/{athleteId}", TEST_ENTRENADOR_ID, TEST_ATHLETE2_ID))
			.andExpect(view().name("redirect:/entrenadores/{entrenadorId}"));
		}
	
	  @WithMockUser(value = "spring")
	  @Test
	  void testDeleteEntrenador() throws Exception {
		  mockMvc.perform(get("/entrenadores/delete/{entrenadorId}", TEST_ENTRENADOR_ID))
		  .andExpect(view().name("entrenadores/entrenadorList"));
}
}
