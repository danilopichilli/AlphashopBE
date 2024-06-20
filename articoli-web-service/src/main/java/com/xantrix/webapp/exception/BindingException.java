package com.xantrix.webapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BindingException extends Exception {

    private static final long serialVersionUID = -1646083143194195402L;

    private String messaggio;

    public BindingException(String messaggio) {
        super(messaggio);
    }

}
