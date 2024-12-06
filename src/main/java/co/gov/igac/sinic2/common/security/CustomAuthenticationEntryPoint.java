package co.gov.igac.sinic2.common.security;

import co.gov.igac.sinic2.common.api.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * Manejador para errores de autenticación (HTTP 401 Unauthorized).
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Registra el módulo para LocalDateTime
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Usa formato ISO-8601
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException) throws IOException {
        // Construimos el objeto ApiErrorResponse
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            HttpServletResponse.SC_UNAUTHORIZED,
            "Unauthorized",
            "No está autenticado para acceder a este recurso",
            request.getRequestURI()
        );

        // Configuramos la respuesta como JSON
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Convertimos el objeto ApiErrorResponse a JSON y lo escribimos en la respuesta
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
