package com.xantrix.webapp.service;

import com.xantrix.webapp.model.dto.UtentiDTO;
import com.xantrix.webapp.model.document.Utenti;

import java.util.List;

public interface UtenteService {

    void createUser(UtentiDTO dto);

    Utenti findById(String id);

    Utenti findByUsername(String nome);

    List<Utenti> findAll();

    void deleteUserById(Utenti utente);
}
