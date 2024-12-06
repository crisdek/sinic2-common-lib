package co.gov.igac.sinic2.common.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import co.gov.igac.sinic2.common.api.ApiErrorResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
 
    private final JwtTokenValidator jwtTokenValidator;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtTokenValidator jwtTokenValidator) {
        this.jwtTokenValidator = jwtTokenValidator;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Registra el módulo para LocalDateTime
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Usa formato ISO-8601
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Sigue la cadena sin autenticar
            return;
        }

        String token = authHeader.substring(7); // Elimina "Bearer " del encabezado

        try {
            // Validar el token y extraer los claims
            Claims claims = jwtTokenValidator.validateToken(token);

            // Crear el objeto de autenticación usando los claims
            PreAuthenticatedAuthenticationToken authentication =
                    new PreAuthenticatedAuthenticationToken(
                            claims.getSubject(), // El usuario (e.g., correo electrónico)
                            null, // No hay credenciales
                            null // Se pueden agregar roles más adelante
                    );

            // Configurar el contexto de seguridad de Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            // Token inválido o expirado
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

            return;
        }

        filterChain.doFilter(request, response);
    }
}
