package co.gov.igac.sinic2.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Clase base para excepciones personalizadas.
 *
 * <p>Esta clase permite la creación de excepciones específicas asociadas a códigos
 * HTTP y mensajes personalizados, facilitando el manejo de errores estandarizados.</p>
 *
 * <p>Se puede extender para crear excepciones específicas como {@link ApiBadRequestException}
 * o {@link ApiNotFoundException}, o usarse directamente con un mensaje y código HTTP.</p>
 *
 * Ejemplo de uso:
 * <pre>
 * throw new CustomException(HttpStatus.BAD_REQUEST, "Solicitud mal formada");
 * </pre>
 *
 * Propiedades:
 * <ul>
 * <li> status: Código de estado HTTP asociado al error.</li>
 * <li> message: Mensaje descriptivo del error.</li>
 */

@ResponseStatus
public class ApiCustomException extends RuntimeException {
    private final HttpStatus status;

    public ApiCustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
