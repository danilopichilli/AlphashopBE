package com.xantrix.webapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DuplicateException  extends Exception{

    private static final long serialVersionUID = 512817164635589450L;

    private String messaggio;

    public DuplicateException(String messaggio) {
        super(messaggio);
    }
}
