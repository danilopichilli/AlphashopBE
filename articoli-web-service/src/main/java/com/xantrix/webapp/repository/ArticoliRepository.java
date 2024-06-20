package com.xantrix.webapp.repository;

import com.xantrix.webapp.entity.Articoli;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
* estendere la classe del paging and sorting permette di attivare appunto dei metodi
* con cui eseguire il paging and sorting dai record che si ottengono dal db
* */
public interface ArticoliRepository extends PagingAndSortingRepository<Articoli, String> {

    @Query(value = "SELECT * FROM Articoli WHERE DESCRIZIONE LIKE :desArt", nativeQuery = true)
    List<Articoli> SelByDescrizioneLike(@Param("desArt") String descrizione);

    List<Articoli> findByDescrizioneLike(String descrizione, Pageable pageable);

    Articoli findByCodArt(String codArt);
}
