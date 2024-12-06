package co.gov.igac.sinic2.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.gov.igac.sinic2.common.api.ApiErrorResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador para excepciones relacionadas con validaciones.
 *
 * <p>Este manejador captura errores de validación generados por Bean Validation
 * (como {@link MethodArgumentNotValidException}) y genera una respuesta detallada
 * con los campos específicos que fallaron.</p>
 *
 * Ejemplo de respuesta JSON:
 * <pre>
 * {
 *   "status": 400,
 *   "error": "Bad Request",
 *   "message": "Error de validación",
 *   "timestamp": "2024-11-28T16:45:00",
 *   "path": "/api/resource",
 *   "validationErrors": {
 *     "campo1": "El campo es obligatorio",
 *     "campo2": "Debe ser un número válido"
 *   }
 * }
 * </pre>
 *
 * Maneja:
 * <ul>
 * <li> {@link MethodArgumentNotValidException}: Cuando los parámetros de un controlador
 *   fallan las validaciones especificadas en las anotaciones (como `@NotNull`, `@Size`, etc.).</li>
 */

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiErrorResponse errorResponse = new ApiErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            null,
            "Error de validación: uno o más campos no cumplen con las restricciones.",
            null
        );
        errorResponse.setDetails(errors);

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
