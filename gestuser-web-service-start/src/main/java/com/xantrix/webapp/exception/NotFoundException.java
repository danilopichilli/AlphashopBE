package com.xantrix.webapp.exception;

import lombok.Data;

@Data
public class NotFoundException extends Exception{

    private static final long serialVersionUID = -5620645408945016734L;

    private String messaggio = "Elemento Ricercato Non Trovato!";

    public NotFoundException(String message) {
        super(message);
    }
}
