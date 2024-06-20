package com.xantrix.webapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DuplicateException extends Exception {

    private static final long serialVersionUID = -5620645408945016734L;

    private String messaggio = "Elemento Duplicato!";

    public DuplicateException(String message) {
        super(message);
    }
}
