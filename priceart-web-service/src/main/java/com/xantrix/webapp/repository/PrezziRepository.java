package com.xantrix.webapp.repository;

import com.xantrix.webapp.entity.DettListini;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PrezziRepository extends JpaRepository<DettListini, Integer> {

    //@Query(value = "SELECT * FROM dettlistini WHERE codArt = ?1 AND idList = ?2", nativeQuery = true)
    @Query(value = "SELECT d FROM Listini l JOIN DettListini d ON l.id = d.listino.id WHERE d.codArt = ?1 AND l.id = ?2")
    DettListini findByCodArtAndIdList(String codArt, String idList);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM dettlistini WHERE codArt = ?1 AND idList = ?2", nativeQuery = true)
    void deleteByCodArtAndIdList(String codArt, String idList);
}
