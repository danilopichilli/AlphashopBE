package com.xantrix.webapp.UnitTest.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.xantrix.webapp.PriceartWebServiceApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.xantrix.webapp.entity.DettListini;
import com.xantrix.webapp.entity.Listini;
import com.xantrix.webapp.repository.ListinoRepository;

@TestPropertySource(locations="classpath:application-list100.properties")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PriceartWebServiceApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrezziControllerTest 
{
	 	private MockMvc mockMvc;

	    @Autowired
		private WebApplicationContext wac;
	    
	    @Autowired
	    private ListinoRepository listinoRepository;
	    
	    String idList = "100";
	    String idList2 = "101";
	    String codArt = "002000301";
	    BigDecimal prezzo = new BigDecimal("1.0");
	    BigDecimal prezzo2 = new BigDecimal("2.0");

		@Before
		public void setup()
		{
			this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
			
			//Inserimento Dati Listino 100
			InsertDatiListino(idList,"Listino Test 100", codArt, prezzo);
			
			//Inserimento Dati Listino 101
			InsertDatiListino(idList2,"Listino Test 101", codArt, prezzo2);

	    }
		
		private void InsertDatiListino(String IdList, String Descrizione, String CodArt, BigDecimal Prezzo)
		{
			
			Listini listinoTest = new Listini(IdList,Descrizione,"No");
	    	
	    	Set<DettListini> dettListini = new HashSet<>();
	    	DettListini dettListTest = new DettListini(CodArt,Prezzo, listinoTest);
	    	dettListini.add(dettListTest);
	    	
	    	listinoTest.setDettListini(dettListini);
	    	
	    	listinoRepository.save(listinoTest);
		}

	@Test
		public void A_testGetPrzCodArt() throws Exception
		{
			mockMvc.perform(MockMvcRequestBuilders.get("/api/prezzi/" + codArt)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").value("1.0")) 
				.andReturn();
		}
		
		@Test
		public void A_testGetPrzCodArt2() throws Exception
		{
			String Url = String.format("/api/prezzi/%s/%s", codArt, idList2);
					
			mockMvc.perform(MockMvcRequestBuilders.get(Url)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").value("2.0")) 
				.andReturn();
		}
		
		@Test
		public void B_testDelPrezzo() throws Exception
		{
			String Url = String.format("/api/prezzi/elimina/%s/%s/", codArt, idList);
			
			mockMvc.perform(MockMvcRequestBuilders.delete(Url)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.code").value("200 OK"))
					.andExpect(jsonPath("$.message").value("Eliminazione Prezzo Eseguita Con Successo"))
					.andDo(print());
		}
		
		@After
		public void ClearData()
		{
			Optional<Listini> listinoTest = listinoRepository.findById(idList);
	    	listinoRepository.delete(listinoTest.get());
	    	
	    	listinoTest = listinoRepository.findById(idList2);
	    	listinoRepository.delete(listinoTest.get());
		}
		
		

}
