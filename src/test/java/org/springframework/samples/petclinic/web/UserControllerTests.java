package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.AthleteService;
import org.springframework.samples.petclinic.service.EntrenadorService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=UserController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class UserControllerTests {
	
	@MockBean
	private EntrenadorService entrenadorService;

	@MockBean
	private UserService userService;
	
	@MockBean
	private AthleteService athleteService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/users/new")).andExpect(status().isOk()).andExpect(view().name("users/tipo"));
	}
	
	@WithMockUser(value = "spring")
	@Test
    void testInitCreationAthleteUserForm() throws Exception {
		mockMvc.perform(get("/users/newDeportista")).andExpect(status().isOk()).andExpect(model().attributeExists("athlete"))
		.andExpect(view().name("users/registarNuevoDeportista"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationEntrenadorUserForm() throws Exception {
		mockMvc.perform(get("/users/newEntrenador")).andExpect(status().isOk()).andExpect(model().attributeExists("entrenador"))
		.andExpect(view().name("users/registrarNuevoEntrenador"));
}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationAthleteUserFormSuccess() throws Exception {
		mockMvc.perform(post("/users/newDeportista")
				.param("nombre", "Julian").param("apellidos", "Fernandez")
				.param("dni","43251426B").param("email", "")
				.param("height", "1.80").param("weight", "80")
				.param("user.username","user1").param("user.password","userpass1")
						.with(csrf()))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationAthleteUserFormFailed() throws Exception {
		mockMvc.perform(post("/users/newDeportista")
				.param("nombre", "Julian").param("apellidos", "Fernandez")
				.param("dni","43251426Bs").param("email", "")
				.param("height", "1.80").param("weight", "80")
				.param("user.username","user1").param("user.password","userpass1")
						.with(csrf()))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("users/registarNuevoDeportista"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationTorneoUserFormSuccess() throws Exception {
		mockMvc.perform(post("/users/newEntrenador")
				.param("nombre", "Julian").param("apellidos", "Fernandez")
				.param("dni","43251426B").param("email", "")
				.param("user.username","user2").param("user.password","userpass2")
						.with(csrf()))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationTorneoUserFormFailed() throws Exception {
		mockMvc.perform(post("/users/newEntrenador")
				.param("nombre", "Julian").param("apellidos", "Fernandez")
				.param("dni","43251426Bs").param("email", "")
				.param("user.username","user2").param("user.password","userpass2")
						.with(csrf()))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("users/registrarNuevoEntrenador"));
	}
	
}
