package dev.hotel.controleur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@WebMvcTest(ClientCtrl.class)
public class ClientCtrlTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ClientRepository clientRepository;
	
	@Test
	void findClientTest() throws Exception{
		
		List<Client> clients = new ArrayList<>();
		Client client1 = new Client("Odd", "Ross");
		client1.setUuid(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"));
		clients.add(client1);
		Mockito.when(clientRepository.findAll(PageRequest.of(0, 1)).toList()).thenReturn(clients);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.nom").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Odd"));
		
	}

}
