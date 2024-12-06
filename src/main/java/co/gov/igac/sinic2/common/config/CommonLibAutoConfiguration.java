package co.gov.igac.sinic2.common.config;

import co.gov.igac.sinic2.common.exception.GlobalExceptionHandler;
import co.gov.igac.sinic2.common.security.DefaultSecurityConfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Configuración automática para la librería sinic2-common-lib.
 * Proporciona componentes y configuraciones reutilizables para proyectos dependientes.
 */
@Configuration
@Import({
    DefaultSecurityConfig.class // Importar configuración de seguridad
})
public class CommonLibAutoConfiguration {

    /**
     * Manejador global de excepciones.
     * Solo se registra si no hay otro manejador definido en el proyecto dependiente.
     */
    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}