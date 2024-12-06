package co.gov.igac.sinic2.common.api;

/**
 * Representa una respuesta genérica estándar para las operaciones de la API.
 *
 * @param <T> El tipo de datos incluidos en la respuesta.
 *
 * Ejemplo de uso:
 * <pre>
 *     ApiResponse<String> response = ApiResponse.success("Operación exitosa");
 * </pre>
 */
public class ApiGenericResponse<T> {
    private int status;
    private String message;
    private T data;

    public ApiGenericResponse() {
    }

    public ApiGenericResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ApiGenericResponse<T> success(T data) {
        return new ApiGenericResponse<>(200, "Success", data);
    }

    public static ApiGenericResponse<Void> error(int status, String message) {
        return new ApiGenericResponse<>(status, message, null);
    }

    // Implementación del patrón Builder
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private int status;
        private String message;
        private T data;

        public Builder<T> status(int status) {
            this.status = status;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiGenericResponse<T> build() {
            return new ApiGenericResponse<>(status, message, data);
        }
    }
}