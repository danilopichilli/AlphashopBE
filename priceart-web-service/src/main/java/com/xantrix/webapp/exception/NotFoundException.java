package com.xantrix.webapp.exception;

import lombok.*;

@Data
public class NotFoundException extends Exception{


    private static final long serialVersionUID = -8729169303699924451L;

    private String messaggio = "Elemento Ricercato Non Trovato!";

    public NotFoundException(String messaggio) {
        super(messaggio);
    }
}