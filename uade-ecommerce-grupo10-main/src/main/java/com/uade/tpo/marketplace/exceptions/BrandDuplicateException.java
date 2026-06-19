package com.uade.tpo.marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Esta anotación permite que Spring devuelva automáticamente un código 409 (Conflict)
@ResponseStatus(code = HttpStatus.CONFLICT)
public class BrandDuplicateException extends Exception {

    public BrandDuplicateException() {
        super("La marca ya existe en el sistema.");
    }

    public BrandDuplicateException(String message) {
        super(message);
    }
}
