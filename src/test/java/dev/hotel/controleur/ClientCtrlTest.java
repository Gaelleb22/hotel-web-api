package dev.hotel.controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;
import dev.hotel.service.ClientService;

@WebMvcTest(ClientCtrl.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientCtrlTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private ClientService clientService;
	
	@BeforeEach
	void setUp() {
		List<Client> clients = new ArrayList<>();
		Client client1 = new Client("Odd", "Ross");
		client1.setUuid(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"));
		clients.add(client1);
		
		
		Client client2 = new Client("Lourson", "Winnie");
		client2.setUuid(UUID.fromString("2bd0937d-43f0-4612-aa0c-2c398b6b1298"));
		clients.add(client2);
		
		Client client3 = new Client("Mouri", "Ran");
		client3.setUuid(UUID.fromString("747c41b7-f164-43f7-86ad-6a42f47c6120"));
		clients.add(client3);
		
		//mock test findClientTest()
		Mockito.when(clientService.findAll(0, 3)).thenReturn(clients);
		
		//mock test findClientByUUIDTestCorrect()
		Optional<Client> client = Optional.of(clients.get(2));
		Mockito.when(clientService.findByUuid(UUID.fromString("747c41b7-f164-43f7-86ad-6a42f47c6120"))).thenReturn(client);
		
	}
	
	@Test
	void findClientTest() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=3"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Odd"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].nom").value("Lourson"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[2].prenoms").value("Ran"));
		
	}
	
	@Test
	void findClientByUUIDTestCorrect() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/clients/747c41b7-f164-43f7-86ad-6a42f47c6120"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.nom").isNotEmpty())
		.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Mouri"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.prenoms").isNotEmpty())
		.andExpect(MockMvcResultMatchers.jsonPath("$.prenoms").value("Ran"));
		
	}
	
	@Test
	void findClientByUUIDTestUuidInvalide() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/clients/747c41b7f164-43f786ad6a42f47c6120"))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Uuid invalide"));
		
	}
	
	@Test
	void findClientByUUIDTestUuidNotFound() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/clients/747c41b7-f164-43f7-86ad-6a42f47c61"))
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Client non trouvé"));
		
	}
	
	@Test
	void creerClientTestOk() throws Exception{
		
		Client clientCreer = new Client();
		clientCreer.setNom("Au bois Dormant");
		clientCreer.setPrenoms("Aurore");
		clientCreer.setUuid(UUID.fromString("747c41b7-f164-43f7-86ad-6a42f47c5320"));
		Mockito.when(clientService.creer("Au bois Dormant", "Aurore")).thenReturn(clientCreer);
		String param = "{ \"nom\":\"Au bois Dormant\", \"prenoms\":\"Aurore\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/clients").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(param))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Au bois Dormant"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.prenoms").value("Aurore"));
	}
	
	@Test
	void creerClientTestNomManquant() throws Exception{
		String param = "{ \"nom\":, \"prenoms\":\"Aurore\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/clients").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(param))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void creerClientTestPrenomTropCourt() throws Exception{
		String param = "{ \"nom\":\"Au bois Dormant\", \"prenoms\":\"A\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/clients").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(param))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
