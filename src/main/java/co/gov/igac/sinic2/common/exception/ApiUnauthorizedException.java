package co.gov.igac.sinic2.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción para manejar accesos no autorizados (HTTP 401 - Unauthorized).
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ApiUnauthorizedException extends RuntimeException {
    public ApiUnauthorizedException(String message) {
        super(message);
    }

    public ApiUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}

