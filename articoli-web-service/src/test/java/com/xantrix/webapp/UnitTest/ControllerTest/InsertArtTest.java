package com.xantrix.webapp.UnitTest.ControllerTest;

import com.xantrix.webapp.ArticoliWebServiceApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xantrix.webapp.entity.Articoli;
import com.xantrix.webapp.repository.ArticoliRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ArticoliWebServiceApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InsertArtTest
{
	 
    private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ArticoliRepository articoliRepository;
	
	@Before
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	String JsonData =
			"{\r\n" +
			"	\"codArt\": \"123Test\",\r\n" +
			"	\"descrizione\": \"Articolo Unit Test prova\",\r\n" +
			"	\"um\": \"PZ\",\r\n"+
			"	\"codStat\": \"TESTART\",\r\n"+
			"	\"pzCart\": 6,\r\n"+
			"	\"pesoNetto\": 1.75 ,\r\n"+
			"	\"idStatoArt\": \"1\",\r\n"+
			"	\"dataCreaz\": \"2019-05-14\",\r\n"+
			"	\"barcode\": [\n" +
					"	{\r\n" +
							"\"barcode\": \"12345678\",\r\n" +
								"\"idTipoArt\": \"CP\"\r\n" +
					"	}\r\n"+
						"],\n" +
			"	\"ingredienti\": null,\r\n" +
			"	\"iva\": " +
						"{\r\n" +
							"\"idIva\": 22,\r\n" +
							"\"descrizione\": \"IVA RIVENDITA 22%\",\r\n"+
							"\"aliquota\": 22\r\n"+
						"},\r\n" +
			"	\"famAssort\": " +
						"{\r\n" +
							"\"id\": 1,\r\n"+
							"\"descrizione\": \"DROGHERIA ALIMENTARE\"\r\n"+
						"}\r\n" +
			"}";

	@Test
	public void A_testInsArticolo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/articoli/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.code").value("200 OK"))
				.andExpect(jsonPath("$.message").value("Inserimento Articolo 123Test Eseguita Con Successo"))
				.andDo(print());

		assertThat(articoliRepository.findByCodArt("123Test"))
		.extracting(Articoli::getCodArt)
		.isEqualTo("123Test");
	}
	
	@Test
	public void B_testErrInsArticolo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/articoli/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable())
				.andExpect(jsonPath("$.codice").value(406))
				.andExpect(jsonPath("$.messaggio").value("Articolo 123Test presente in anagrafica! Impossibile utilizzare il metodo POST"))
				.andDo(print());
	}
	
	String ErrJsonData =
			"{\r\n" +
					"	\"codArt\": \"123Test\",\r\n" +
					"	\"descrizione\": \"\",\r\n" + //Articolo privo di descrizione
					"	\"um\": \"PZ\",\r\n"+
					"	\"codStat\": \"TESTART\",\r\n"+
					"	\"pzCart\": 6,\r\n"+
					"	\"pesoNetto\": 1.75 ,\r\n"+
					"	\"idStatoArt\": \"1\",\r\n"+
					"	\"dataCreaz\": \"2019-05-14\",\r\n"+
					"	\"barcode\": [\n" +
					"	{\r\n" +
					"\"barcode\": \"12345678\",\r\n" +
					"\"idTipoArt\": \"CP\"\r\n" +
					"	}\r\n"+
					"],\n" +
					"	\"ingredienti\": null,\r\n" +
					"	\"iva\": " +
					"{\r\n" +
					"\"idIva\": 22,\r\n" +
					"\"descrizione\": \"IVA RIVENDITA 22%\",\r\n"+
					"\"aliquota\": 22\r\n"+
					"},\r\n" +
					"	\"famAssort\": " +
					"{\r\n" +
					"\"id\": 1,\r\n"+
					"\"descrizione\": \"DROGHERIA ALIMENTARE\"\r\n"+
					"}\r\n" +
					"}";
	
	@Test
	public void C_testErrInsArticolo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/articoli/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ErrJsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codice").value(400))
				.andDo(print());
	}

	String JsonDataMod =
			"{\r\n" +
					"	\"codArt\": \"123Test\",\r\n" +
					"	\"descrizione\": \"Articolo Unit Test prova\",\r\n" +
					"	\"um\": \"PZ\",\r\n"+
					"	\"codStat\": \"TESTART\",\r\n"+
					"	\"pzCart\": 6,\r\n"+
					"	\"pesoNetto\": 1.75 ,\r\n"+
					"	\"idStatoArt\": \"2\",\r\n"+	//Modifica Stato Articolo a 2
					"	\"dataCreaz\": \"2019-05-14\",\r\n"+
					"	\"barcode\": [\n" +
					"	{\r\n" +
					"\"barcode\": \"12345678\",\r\n" +
					"\"idTipoArt\": \"CP\"\r\n" +
					"	}\r\n"+
					"],\n" +
					"	\"ingredienti\": null,\r\n" +
					"	\"iva\": " +
					"{\r\n" +
					"\"idIva\": 22,\r\n" +
					"\"descrizione\": \"IVA RIVENDITA 22%\",\r\n"+
					"\"aliquota\": 22\r\n"+
					"},\r\n" +
					"	\"famAssort\": " +
					"{\r\n" +
					"\"id\": 1,\r\n"+
					"\"descrizione\": \"DROGHERIA ALIMENTARE\"\r\n"+
					"}\r\n" +
					"}";

	@Test
	public void D_testUpdArticolo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.put("/api/articoli/modifica")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonDataMod)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());

		assertThat(articoliRepository.findByCodArt("123Test"))
		.extracting(Articoli::getIdStatoArt)
		.isEqualTo("2");
	}
	
	@Test
	public void E_testDelArticolo() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/articoli/elimina/123Test")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("200 OK"))
				.andExpect(jsonPath("$.message").value("Eliminazione Articolo 123Test Eseguita Con Successo"))
				.andDo(print());
	}


	
	
	
}
