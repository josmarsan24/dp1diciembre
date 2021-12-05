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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Genero;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.TorneoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=TorneoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class TorneoControllerTests {
	
	private static final int TEST_Torneo_ID = 1;
	private static final int TEST_Torneo_ID2 = 2;
	private static final int TEST_Athlete_ID = 1;
	
	
	@MockBean
	private TorneoService torneoService;
	
	@MockBean
	private AthleteService athleteService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Torneo torneo1;
	private Torneo torneo2;
	private Athlete ath;
	
	@BeforeEach
	void setup() {
		ath = new Athlete();
		torneo1 = new Torneo();
		torneo2 = new Torneo();
		ath.setId(TEST_Athlete_ID);
		ath.setNombre("Nombre");
		ath.setApellidos("Apellidos");
		ath.setWeight(70);
		ath.setHeight("1.77");
		ath.setDni("43251426F");
		ath.setEmail("");
		ath.setGenero(Genero.HOMBRE);
		
		Set<Athlete> participantes = new HashSet<Athlete>();
		torneo1.setId(TEST_Torneo_ID);
		torneo1.setName("Torneo Test");
		torneo1.setFechaInicio(LocalDate.parse("2022-12-03"));
		torneo1.setFechaFin(LocalDate.parse("2022-12-10"));
		torneo1.setParticipantes(participantes);
		torneo2.setId(TEST_Torneo_ID2);
		torneo2.setName("Torneo Test2");
		torneo2.setFechaInicio(LocalDate.parse("2020-12-03"));
		torneo2.setFechaFin(LocalDate.parse("2020-12-10"));
		torneo2.setParticipantes(participantes);
		given(athleteService.findAthleteById(TEST_Athlete_ID)).willReturn(ath);
		given(torneoService.findTorneoById(TEST_Torneo_ID)).willReturn(torneo1);
		given(torneoService.findTorneoById(TEST_Torneo_ID2)).willReturn(torneo2);
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/torneos/new")).andExpect(status().isOk()).andExpect(model().attributeExists("torneo"))
		.andExpect(view().name("torneos/editTorneos"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testListTorneos() throws Exception {
	mockMvc.perform(get("/torneos")).andExpect(status().isOk()).andExpect(model().attributeExists("torneos"))
			.andExpect(view().name("torneos/listTorneos"));
	
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormFailed() throws Exception {
		mockMvc.perform(post("/torneos/new").param("name", "").param("fechaInicio", "2022/08/20").param("fechaFin","2022/08/31")
				.with(csrf()))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("torneos/editTorneos"));
	}
	

	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/torneos/new").param("name", "Torneos Test Crear").param("fechaInicio", "2022/08/20").param("fechaFin","2022/08/31")
			.with(csrf()))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("torneos/listTorneos"));
	}
	
	 @WithMockUser(value = "spring")
	  @Test
	  void testDeleteTorneo() throws Exception {
		  mockMvc.perform(get("/torneos/delete/{torneoId}", TEST_Torneo_ID))
		  .andExpect(view().name("torneos/listTorneos"));
	 }
	 
	 @WithMockUser(value = "spring")
		@Test
	void testInitUpdateTorneoForm() throws Exception {
		mockMvc.perform(get("/torneos/edit/{torneoId}",TEST_Torneo_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("torneo"))
				.andExpect(model().attribute("torneo", hasProperty("name", is("Torneo Test"))))
				.andExpect(model().attribute("torneo", hasProperty("fechaInicio", is(LocalDate.parse("2022-12-03")))))
				.andExpect(model().attribute("torneo", hasProperty("fechaFin",is(LocalDate.parse("2022-12-10")))))
				.andExpect(view().name("torneos/editTorneos"));
	}
	 
	 @WithMockUser(value = "spring")
		@Test
	void testUpdateTorneoFormFailed() throws Exception {
		mockMvc.perform(post("/torneos/edit/{torneoId}",TEST_Torneo_ID)
				.param("name", "")
				.param("fechaInicio", "2022/12/03").param("fechaFin","2022/12/10")
				.with(csrf()))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("torneos/editTorneos"));
	}
	 
	 @WithMockUser(value = "spring")
		@Test
	void testUpdateTorneoFormSuccess() throws Exception {
		mockMvc.perform(post("/torneos/edit/{torneoId}",TEST_Torneo_ID)
				.param("name", "Torneos Test Update")
				.param("fechaInicio", "2022/12/03").param("fechaFin","2022/12/10")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/torneos"));
	}
	 

	  @WithMockUser(value = "spring")
	  @Test
	void testShowTorneo() throws Exception {
			mockMvc.perform(get("/torneos/show/{torneoId}",TEST_Torneo_ID)).andExpect(status().isOk())
			.andExpect(model().attributeExists("torneo"))
			.andExpect(model().attribute("torneo", hasProperty("name", is("Torneo Test"))))
			.andExpect(model().attribute("torneo", hasProperty("fechaInicio", is(LocalDate.parse("2022-12-03")))))
			.andExpect(model().attribute("torneo", hasProperty("fechaFin",is(LocalDate.parse("2022-12-10")))))
			.andExpect(view().name("torneos/torneoDetails"));
		}
	  
	  @WithMockUser(value = "spring")
	  @Test
		void testBuscarParticipante() throws Exception {
			mockMvc.perform(get("/torneos/show/{torneoId}/add", TEST_Torneo_ID))
			.andExpect(model().attributeExists("torneo"))
			.andExpect(model().attributeExists("participantes"))
			.andExpect(view().name("torneos/addParticipantes"));
		}
	  
	  @WithMockUser(value = "spring")
	  @Test
		void testBuscarParticipanteTorneoAntiguo() throws Exception {
			mockMvc.perform(get("/torneos/show/{torneoId}/add", TEST_Torneo_ID2))
			.andExpect(view().name("torneos/torneoDetails"));
		}
	  
	  @WithMockUser(value = "spring")
			@Test
			void testAñadirParticipante() throws Exception {
				mockMvc.perform(get("/torneos/show/{torneoId}/add/{athleteId}", TEST_Torneo_ID, TEST_Athlete_ID))
				.andExpect(view().name("redirect:/torneos/show/{torneoId}"));
			}
}
