package com.xantrix.webapp.entity.model;

import lombok.Data;

import java.util.List;

@Data
public class Utenti {
    private String id;
    private String username;
    private String password;
    private String attivo;

    private List<String> ruoli;
}
