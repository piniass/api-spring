package com.example.apipractica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class ZapatillaBadRequestException extends ZapatillaException {
    public ZapatillaBadRequestException(String mensaje) {
        super(mensaje);
    }
}
