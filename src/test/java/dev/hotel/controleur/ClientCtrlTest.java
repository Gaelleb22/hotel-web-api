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

@WebMvcTest(ClientCtrl.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientCtrlTest {
	
	@Autowired
	MockMvc mockMvc;
	
	
	@MockBean
	ClientRepository clientRepository;
	
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
		Page<Client> p = new PageImpl<>(clients);
		Mockito.when(clientRepository.findAll(PageRequest.of(0, 1))).thenReturn(p);
		
		//mock test findClientByUUIDTestCorrect()
		Optional<Client> client = Optional.of(clients.get(2));
		Mockito.when(clientRepository.findByUuid(UUID.fromString("747c41b7-f164-43f7-86ad-6a42f47c6120"))).thenReturn(client);
		
		Client clientCreer = new Client();
		Mockito.when(clientRepository.save(clientCreer)).thenReturn(clientCreer);
		
	}
	
	@Test
	void findClientTest() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Odd"));
		
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
	void creerClient() throws Exception{
		String param = "{ \"nom\":\"Au bois Dormant\", \"prenoms\":\"Aurore\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/clients")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(param)
				/*.param("nom", "Au bois Dormant").param("prenoms", "Aurore")*/)
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
