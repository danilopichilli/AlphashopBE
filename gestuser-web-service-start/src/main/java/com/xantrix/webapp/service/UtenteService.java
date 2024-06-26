package com.xantrix.webapp.service;

import com.xantrix.webapp.model.dto.UtenteDTO;
import com.xantrix.webapp.model.document.Utente;

import java.util.List;

public interface UtenteService {

    void createSimpleUser(UtenteDTO dto);

    void createUser(UtenteDTO dto);

    UtenteDTO findById(String id);

    UtenteDTO findByUsername(String nome);

    List<Utente> findAll();

    void deleteUserById(String id);

}
