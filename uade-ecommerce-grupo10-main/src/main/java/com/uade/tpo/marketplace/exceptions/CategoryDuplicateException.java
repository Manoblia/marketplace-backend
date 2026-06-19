package com.uade.tpo.marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando se intenta crear una categoría con un nombre
 * que ya existe en la base de datos.
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
public class CategoryDuplicateException extends Exception {

    public CategoryDuplicateException() {
        super("La categoría ya existe.");
    }

    public CategoryDuplicateException(String message) {
        super(message);
    }
}