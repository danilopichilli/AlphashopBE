package com.xantrix.webapp.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class UtentiDTO {

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
