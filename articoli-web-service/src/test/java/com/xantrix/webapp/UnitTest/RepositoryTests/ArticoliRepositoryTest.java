package com.xantrix.webapp.UnitTest.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.xantrix.webapp.ArticoliWebServiceApplication;
import com.xantrix.webapp.entity.Articoli;
import com.xantrix.webapp.repository.ArticoliRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ArticoliWebServiceApplication.class)
@SpringBootTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArticoliRepositoryTest
{
	@Autowired
	private ArticoliRepository articoliRepository;

	@Test
	public void TestfindByDescrizioneLike()
	{
		List<Articoli> items = articoliRepository.SelByDescrizioneLike("ACQUA ULIVETO%"); //questo metodo non utilizza spring data jpa
		assertEquals(2, items.size());
	}
	
	@Test
	public void TestfindByDescrizioneLikePage()
	{
		List<Articoli> items = articoliRepository.findByDescrizioneLike("ACQUA%",PageRequest.of(0, 10)); //PageRequest applica un paging e si stanno selezionando esattamente 10 articoli su tutti quelli che sono disponibili
		assertEquals(10, items.size());
	}

	
	@Test
	public void TestfindByCodArt() throws Exception
	{
		assertThat(articoliRepository.findByCodArt("002000301"))
				.extracting(Articoli::getDescrizione)
				.isEqualTo("ACQUA ULIVETO 15 LT");
				
	}
	

}
