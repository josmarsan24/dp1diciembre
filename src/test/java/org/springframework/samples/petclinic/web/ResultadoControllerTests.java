package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.model.Resultado;
import org.springframework.samples.petclinic.model.Torneo;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.ResultadoService;
import org.springframework.samples.petclinic.service.TorneoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ResultadoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ResultadoControllerTests {

	private static final int TEST_RESULTADO_ID = 1;
	
	private Resultado res1;
	private Torneo torneo1;
	private Athlete ath;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ResultadoService resultadoService;
	
	@MockBean
	TorneoService torneoService;
	
	@MockBean
	AthleteService athleteService;
	
	@BeforeEach
	void setup() {
		ath = new Athlete();
		torneo1 = new Torneo();
		ath.setId(14);
		ath.setNombre("Nombre");
		ath.setApellidos("Apellidos");
		ath.setWeight(70);
		ath.setHeight("1.77");
		ath.setDni("43251426F");
		ath.setEmail("");
		ath.setGenero(Genero.HOMBRE);
		Set<Athlete> participantes = new HashSet<Athlete>();
		participantes.add(ath);
		torneo1.setId(2);
		torneo1.setName("Torneo Test");
		torneo1.setFechaInicio(LocalDate.parse("2022-12-03"));
		torneo1.setFechaFin(LocalDate.parse("2022-12-10"));
		torneo1.setParticipantes(participantes);
		res1 = new Resultado();
		res1.setId(TEST_RESULTADO_ID);
		given(athleteService.findAthleteById(14)).willReturn(ath);
		given(torneoService.findTorneoById(2)).willReturn(torneo1);
		given(resultadoService.findById(TEST_RESULTADO_ID)).willReturn(res1);
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/torneos/show/{torneoId}/{athleteId}/resultado/new",TEST_RESULTADO_ID,TEST_RESULTADO_ID)).andExpect(status().isOk())
		.andExpect(view().name("resultados/editResultado")).andExpect(model().attributeExists("resultado"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationSancionFormFailed() throws Exception {
	mockMvc.perform(post("/torneos/show/{torneoId}/{athleteId}/resultado/new",2,14).param("posicion", "-1")
						.with(csrf()))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("resultados/editResultado"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationSancionFormSuccess() throws Exception {
	mockMvc.perform(post("/torneos/show/{torneoId}/{athleteId}/resultado/new",2,14).param("posicion", "1")
						.with(csrf()))
	.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/torneos/show/{torneoId}"));
	}

}
