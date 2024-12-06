package co.gov.igac.sinic2.common.security;

import co.gov.igac.sinic2.common.api.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * Manejador para errores de autorización (HTTP 403 Forbidden).
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Manejo de LocalDateTime
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Formato ISO-8601
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        // Construimos el objeto ApiErrorResponse
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            HttpServletResponse.SC_FORBIDDEN,
            "Forbidden",
            "No tiene permisos para acceder a este recurso",
            request.getRequestURI()
        );

        // Configuramos la respuesta como JSON
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Convertimos el objeto ApiErrorResponse a JSON y lo escribimos en la respuesta
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
