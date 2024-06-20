package com.xantrix.webapp.service;

import com.xantrix.webapp.entity.Articoli;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticoliService {

    Iterable<Articoli> selTutti();

    List<Articoli> selByDescrizione(String descrizione);

    List<Articoli> selByDescrizione(String descrizione, Pageable pageable);

    Articoli selByCodArt(String codArt);

    void delArticolo(Articoli articolo);

    void insArticolo(Articoli articolo);
}
