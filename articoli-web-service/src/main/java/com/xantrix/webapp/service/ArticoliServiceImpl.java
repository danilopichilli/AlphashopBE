package com.xantrix.webapp.service;

import com.xantrix.webapp.entity.Articoli;
import com.xantrix.webapp.repository.ArticoliRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
/*
* impostando readOnly a true tutte le query di selezione saranno solo di lettura
* per quanto riguarda invece le query di inserimento saranno sottoposte a transazione
* per l'inserimento e la modifica
* */
public class ArticoliServiceImpl implements ArticoliService {

    ArticoliRepository articoliRepository;

    public ArticoliServiceImpl(ArticoliRepository articoliRepository) {
        this.articoliRepository = articoliRepository;
    }

    @Override
    public Iterable<Articoli> selTutti() {
        return articoliRepository.findAll();
    }

    @Override
    public List<Articoli> selByDescrizione(String descrizione) {
        return articoliRepository.SelByDescrizioneLike(descrizione);
    }

    @Override
    public List<Articoli> selByDescrizione(String descrizione, Pageable pageable) {
        return articoliRepository.findByDescrizioneLike(descrizione, pageable);
    }

    @Override
    public Articoli selByCodArt(String codArt) {
        if(!codArt.isEmpty()) {
            Articoli articolo = articoliRepository.findByCodArt(codArt);
            if(articolo != null) {
                articolo.setUm(articolo.getUm().trim());
                articolo.setIdStatoArt(articolo.getIdStatoArt().trim());
                return articolo;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void delArticolo(Articoli articolo) {
        articoliRepository.delete(articolo);
    }

    @Override
    @Transactional
    public void insArticolo(Articoli articolo) {
        articoliRepository.save(articolo);
    }
}
