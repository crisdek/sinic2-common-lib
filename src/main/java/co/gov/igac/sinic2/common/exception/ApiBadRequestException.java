package co.gov.igac.sinic2.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción para manejar solicitudes mal formadas (HTTP 400 - Bad Request).
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiBadRequestException extends RuntimeException {
    public ApiBadRequestException(String message) {
        super(message);
    }
}
