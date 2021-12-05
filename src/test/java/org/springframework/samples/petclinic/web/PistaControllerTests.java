package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Pista;
import org.springframework.samples.petclinic.service.PistaService;
import org.springframework.samples.petclinic.service.TorneoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PistaController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), 
excludeAutoConfiguration = SecurityConfiguration.class)
public class PistaControllerTests {

	private static final int TEST_PISTA_ID = 1;

	@MockBean
	private PistaService pistaService;
	
	@MockBean
	private TorneoService torneoService;

	@Autowired
	private MockMvc mockMvc;

	private Pista p1;

	@BeforeEach
	void setup() {
		p1 = new Pista();
		p1.setId(TEST_PISTA_ID);
		p1.setAforo(100);
		p1.setCiudad("Ciudad");
		p1.setName("Pista");
		given(pistaService.findPistaById(TEST_PISTA_ID)).willReturn(p1);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/pistas/new")).andExpect(status().isOk()).andExpect(model().attributeExists("pista"))
				.andExpect(view().name("pistas/editPista"));

	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/pistas/new").param("aforo", "1000").param("ciudad", "Ciudad").param("nombre","Pista")
						.with(csrf()))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/pistas"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormFailure() throws Exception {
		mockMvc.perform(post("/pistas/new").param("aforo", "-100").param("ciudad", "").param("nombre","")
						.with(csrf()))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("pistas/editPista"));
}
	
	

	@WithMockUser(value = "spring")
	@Test
	void testListPistas() throws Exception {
		mockMvc.perform(get("/pistas")).andExpect(status().isOk()).andExpect(model().attributeExists("pistas"))
				.andExpect(view().name("pistas/listPistas"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeletePista() throws Exception {
		mockMvc.perform(get("/pistas/delete/{pistaId}/", TEST_PISTA_ID)).andExpect(view().name("pistas/listPistas"));
	}
	

}
