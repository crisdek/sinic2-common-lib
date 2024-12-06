package co.gov.igac.sinic2.common.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import io.jsonwebtoken.security.Keys;


/**
 * Configuración predeterminada de seguridad para la librería sinic2-common-lib.
 *
 * <p>Esta clase proporciona una configuración de seguridad predeterminada que protege todas
 * las rutas por defecto, permitiendo el acceso sin autenticación a:
 * <ul>
 *   <li>Las rutas relacionadas con Swagger (`/swagger-ui/**`, `/v3/api-docs/**`, `/swagger-resources/**`)</li>
 *   <li>Las rutas definidas en la propiedad <code>security.public-urls</code></li>
 * </ul>
 *
 * <p>Además, implementa soporte para Cross-Origin Resource Sharing (CORS) utilizando orígenes
 * configurables a través de la propiedad <code>security.cors.allowed-origins</code>.
 *
 * <h2>Configuraciones personalizables</h2>
 * <p>Los proyectos dependientes pueden sobrescribir los siguientes componentes si necesitan
 * personalizar la seguridad o el manejo de excepciones:
 * <ul>
 *   <li>{@link SecurityFilterChain}: Para definir reglas de autorización específicas.</li>
 *   <li>{@link CorsFilter}: Para configurar reglas avanzadas de CORS.</li>
 *   <li>{@link CustomAuthenticationEntryPoint}: Para manejar errores de autenticación (HTTP 401).</li>
 *   <li>{@link CustomAccessDeniedHandler}: Para manejar accesos denegados (HTTP 403).</li>
 * </ul>
 *
 * <h2>Configuración desde el proyecto dependiente</h2>
 * <p>Los proyectos pueden configurar los siguientes elementos a través de su archivo
 * <code>application.properties</code> o <code>application.yml</code>:
 * <ul>
 *   <li><strong>security.public-urls</strong>: Rutas adicionales permitidas sin autenticación (por defecto, ninguna).</li>
 *   <li><strong>security.cors.allowed-origins</strong>: Lista de orígenes permitidos para solicitudes CORS
 *   (por defecto, todos los orígenes <code>*</code>).</li>
 * </ul>
 *
 * <h2>Ejemplo de configuración en application.yml</h2>
 * <pre>
 * security:
 *   public-urls:
 *     - "/api/public/**"
 *   cors:
 *     allowed-origins:
 *       - "http://localhost:4200"
 *       - "https://my-angular-app.com"
 * </pre>
 *
 * <h2>Comportamiento predeterminado</h2>
 * <p>Si las propiedades no están configuradas, se aplicarán los siguientes valores predeterminados:
 * <ul>
 *   <li><strong>security.public-urls</strong>: Ninguna ruta adicional está permitida sin autenticación.</li>
 *   <li><strong>security.cors.allowed-origins</strong>: Se permite cualquier origen (<code>*</code>).</li>
 * </ul>
 */

 @Configuration
 @EnableWebSecurity
 public class DefaultSecurityConfig {
 
    // Rutas públicas (por defecto, ninguna)
    @Value("${security.public-urls:}")
    private String[] publicUrls;

    // Orígenes permitidos (por defecto, todos)
    @Value("${security.cors.allowed-origins:*}") 
    private List<String> allowedOrigins;

    @Value("${jwt.secret}") // Carga la clave secreta desde application.properties
    private String jwtSecret;


    @Bean
    @ConditionalOnMissingBean
    public JwtTokenValidator jwtTokenValidator() {
        return new JwtTokenValidator(jwtSecret);
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenValidator jwtTokenValidator) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenValidator);

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración CORS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(publicUrls).permitAll() // Rutas públicas configuradas
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll() // Swagger
                .anyRequest().authenticated() // Todas las demás rutas requieren autenticación
            )
            .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(customAuthenticationEntryPoint())
                .accessDeniedHandler(customAccessDeniedHandler())
            );
            
        return http.build();
    }


    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(allowedOrigins); // Orígenes configurados
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build();
        return decoder;
    }

     @Bean
     @ConditionalOnMissingBean
     public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
         return new CustomAuthenticationEntryPoint();
     }
 
     @Bean
     @ConditionalOnMissingBean
     public CustomAccessDeniedHandler customAccessDeniedHandler() {
         return new CustomAccessDeniedHandler();
     }
 }