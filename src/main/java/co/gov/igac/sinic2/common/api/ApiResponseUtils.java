package co.gov.igac.sinic2.common.api;

/**
 * Utilidades para construir respuestas estándar de la API.
 *
 * Métodos disponibles:
 * - {@link #buildSuccessResponse(Object)}: Construye una respuesta exitosa con datos.
 * - {@link #buildErrorResponse(int, String)}: Construye una respuesta de error con código y mensaje.
 *
 * Ejemplo de uso:
 * <pre>
 *     ApiResponse<String> successResponse = ApiResponseUtils.buildSuccessResponse("Datos procesados correctamente");
 *     ApiResponse<Void> errorResponse = ApiResponseUtils.buildErrorResponse(404, "Recurso no encontrado");
 * </pre>
 */
public class ApiResponseUtils {
    public static <T> ApiGenericResponse<T> buildSuccessResponse(T data) {
        return ApiGenericResponse.success(data);
    }

    public static ApiGenericResponse<Void> buildErrorResponse(int status, String message) {
        return ApiGenericResponse.error(status, message);
    }
}
