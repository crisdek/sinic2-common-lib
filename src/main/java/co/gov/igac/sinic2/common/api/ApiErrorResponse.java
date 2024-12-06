package co.gov.igac.sinic2.common.api;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Representa una respuesta estándar para errores en la API.
 * Proporciona detalles adicionales sobre el error ocurrido.
 *
 * Incluye:
 * - Código de estado HTTP
 * - Mensaje de error
 * - Mensaje descriptivo
 * - Detalles adicionales (opcional)
 * - Marca de tiempo del error
 * - Ruta del endpoint donde ocurrió el error
 *
 * Ejemplo de respuesta JSON:
 * <pre>
 * {
 *   "timestamp": "2024-11-28T14:00:00",
 *   "status": 400,
 *   "error": "Bad Request",
 *   "message": "Error de validación: uno o más campos no cumplen con las restricciones.",
 *   "details": {
 *      "campo1": "El campo es obligatorio",
        "campo2": "Debe ser un número válido"
 *   }
 *   "path": "/api/resource"
 * }
 * </pre>
 */

public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    private Map<String, String> details;
    private LocalDateTime timestamp;
    private String path;

    // Mapa de códigos de estado a mensajes estándar
    private static final Map<Integer, String> STATUS_TO_ERROR = Map.of(
        400, "Bad Request",
        401, "Unauthorized",
        403, "Forbidden",
        404, "Not Found",
        409, "Conflict",
        500, "Internal Server Error"
    );

    public ApiErrorResponse() {
    }

    // Constructor principal
    public ApiErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now(); // Genera el timestamp automáticamente
        this.path = path;
        this.error = error != null ? error : getDefaultError(status); // Asigna el error o usa el predeterminado
    }

    // Constructor de conveniencia para casos donde no se pasa el path
    public ApiErrorResponse(int status, String message) {
        this(status, null, message, null); // path se inicializa como null
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // Método para obtener el error predeterminado según el status
    private String getDefaultError(int status) {
        return STATUS_TO_ERROR.getOrDefault(status, "Unknown Error");
    }
}
