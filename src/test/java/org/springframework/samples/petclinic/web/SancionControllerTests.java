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
import org.springframework.samples.petclinic.model.Sancion;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.SancionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=SancionController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class SancionControllerTests {
	
	private static final int TEST_SANCION_ID = 1;
	
	private static final int TEST_ATHLETE_ID = 1;
	
	private Sancion sancion;
	private Athlete ath;
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	SancionService sancionService;
	@MockBean
	AthleteService athleteService;
	
	@BeforeEach
	void setup() {
		sancion = new Sancion();
		sancion.setId(TEST_SANCION_ID);
		sancion.setDescripcion("Cometio una falta grave");
		sancion.setFechaFin(LocalDate.parse("2022-12-03"));
		ath = new Athlete();
		ath.setId(TEST_ATHLETE_ID);
		ath.setNombre("ath1");
		ath.setApellidos("apellidos");
		Set<Sancion> sanciones = new HashSet<Sancion>();
		sanciones.add(sancion);
		ath.setSanciones(sanciones);
		given(this.sancionService.findSancionById(TEST_SANCION_ID)).willReturn(sancion);
		given(this.athleteService.findAthleteById(TEST_ATHLETE_ID)).willReturn(ath);
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/athletes/show/{athleteId}/newSancion",TEST_ATHLETE_ID)).andExpect(status().isOk())
		.andExpect(view().name("athletes/createOrUpdateSancionForm")).andExpect(model().attributeExists("sancion"));
	}
	
	 @WithMockUser(value = "spring")
	  @Test
	  void testDeleteSancion() throws Exception {
		  mockMvc.perform(get("/athletes/show/{athleteId}/delete/{sancionId}",TEST_ATHLETE_ID,TEST_SANCION_ID))
		  .andExpect(view().name("redirect:/athletes/show/{athleteId}"));
}

		@WithMockUser(value = "spring")
	    @Test
	void testProcessCreationSancionFormFailed() throws Exception {
		mockMvc.perform(post("/athletes/show/{athleteId}/newSancion",TEST_ATHLETE_ID).param("descripcion", "").param("fechaFin", "2022/08/31")
							.with(csrf()))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("athletes/createOrUpdateSancionForm"));
	}
		
		@WithMockUser(value = "spring")
	    @Test
	void testProcessCreationSancionFormSuccess() throws Exception {
		mockMvc.perform(post("/athletes/show/{athleteId}/newSancion",TEST_ATHLETE_ID).param("descripcion", "El deportista cometio una falta").param("fechaFin", "2022/08/31")
							.with(csrf()))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/athletes/show/{athleteId}"));
	}
}
