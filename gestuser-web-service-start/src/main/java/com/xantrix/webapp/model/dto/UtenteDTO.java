package com.xantrix.webapp.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UtenteDTO {

    private String id;

    private String nome;

    private String cognome;

    private String email;

    private LocalDate dob;

    private String username;

    private String password;

    private String attivo;

    private List<String> ruoli;
}
