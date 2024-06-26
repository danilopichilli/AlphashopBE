package com.xantrix.webapp.controller;

import com.xantrix.webapp.entity.model.Utente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "GestUserWebService", url = "http://localhost:8019")
public interface UtenteClient {

    @GetMapping("/api/utenti/cerca/username/{username}")
    public Utente getUtente(@PathVariable("username") String username);

}
