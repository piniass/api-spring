package com.example.apipractica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class ZapatillaNotFoundException extends ZapatillaException {
    public ZapatillaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
