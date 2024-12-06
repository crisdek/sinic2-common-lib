package co.gov.igac.sinic2.common.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import co.gov.igac.sinic2.common.api.ApiErrorResponse;

/**
 * Manejador global de excepciones para las aplicaciones basadas en Spring Boot.
 *
 * <p>Esta clase captura y maneja excepciones específicas y genéricas lanzadas
 * durante la ejecución de los controladores REST, proporcionando respuestas 
 * consistentes y estandarizadas a los clientes.</p>
 *
 * <p>Maneja las siguientes excepciones:</p>
 * <ul>
 * <li>{@link ApiCustomException}: Para excepciones personalizadas con códigos HTTP específicos.</li>
 * <li>{@link BadRequestException}: Para errores de solicitud mal formada (HTTP 400).</li>
 * <li>{@link ApiNotFoundException}: Para recursos no encontrados (HTTP 404).</li>
 * <li>{@link ApiUnauthorizedException}: Para accesos no autorizados (HTTP 401).</li>
 * <li>{@link ApiForbiddenException}: Para errores de autorización (HTTP 403).</li>
 * <li>{@link ApiConflictException}: Para conflictos (HTTP 409).</li>
 * <li>{@link Exception}: Para errores genéricos no controlados (HTTP 500).</li>
 * </ul>
 *
 * Ejemplo de respuesta JSON para un error de validación:
 * <pre>
 * {
 *   "status": 400,
 *   "error": "Bad Request",
 *   "message": "Error de validación",
 *   "timestamp": "2024-11-28T16:45:00",
 *   "path": "/api/resource"
 * }
 * </pre>
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(BadRequestException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(ApiNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ApiNotFoundException ex, WebRequest request) {
        System.out.println("Manejador global invocado para ApiNotFoundException");
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(ApiUnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(ApiUnauthorizedException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex, request);
    }

    @ExceptionHandler(ApiForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(ApiForbiddenException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex, request);
    }

    @ExceptionHandler(ApiConflictException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(ApiConflictException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    @ExceptionHandler(ApiCustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomException(ApiCustomException ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            ex.getStatus().value(),
            null,
            ex.getMessage(),
            path
        );
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }


    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, Exception ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            status.value(),
            null,
            ex.getMessage(),
            path
        );
        return ResponseEntity.status(status).body(errorResponse);
    }
}
