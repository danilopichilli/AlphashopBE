package com.xantrix.webapp.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class UtentiDTO {

    private String id;

    private String username;

    private String password;

    private String attivo;

    private List<String> ruoli;
}
