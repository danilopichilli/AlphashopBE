package com.xantrix.webapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BindingException extends Exception{

    private static final long serialVersionUID = 2473986235162428097L;

    private String messaggio;

    public BindingException(String messaggio) {
        super(messaggio);
    }
}
