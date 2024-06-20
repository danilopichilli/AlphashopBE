package com.xantrix.webapp.UnitTest.RepositoryTest;

import com.xantrix.webapp.entity.DettListini;
import com.xantrix.webapp.entity.Listini;
import com.xantrix.webapp.repository.ListinoRepository;
import com.xantrix.webapp.repository.PrezziRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@TestPropertySource(locations = "classpath:application-list1.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrezziRepositoryTest {

    @Autowired
    private PrezziRepository prezziRepository;

    @Autowired
    private ListinoRepository listinoRepository;

    String idList = "100";
    String codArt = "002000301";
    BigDecimal prezzo = new BigDecimal("1.00");

    @Test
    public void A_testInsListino() {
        Listini listinoTest = new Listini(idList, "Listino Test 100","No");

        Set<DettListini> dettListini = new HashSet<>();
        DettListini dettListTest = new DettListini(codArt,prezzo,listinoTest);
        dettListini.add(dettListTest);

        listinoTest.setDettListini(dettListini);

        listinoRepository.save(listinoTest);

        assertThat(listinoRepository.
                findById(idList))
                .isNotEmpty();
    }

    @Test
    public void B_TestfindByCodArtAndIdList() {

        assertThat(prezziRepository.
                findByCodArtAndIdList(codArt,idList))
                .extracting(DettListini::getPrezzo)
                .isEqualTo(prezzo);
    }

    @Test
    @Transactional
    public void C_TestDeletePrezzo() {
        prezziRepository.deleteByCodArtAndIdList(codArt,idList);

        assertThat(prezziRepository
                .findByCodArtAndIdList(codArt,idList))
                .isNull();
    }

    @Test
    public void D_TestDeleteListino() {
        Optional<Listini> listinoTest = listinoRepository.findById(idList);
        listinoRepository.delete(listinoTest.get());

        assertThat(listinoRepository
                .findById(idList))
                .isEmpty();
    }
}
