package com.viju.andaluciaskills.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de configuración para habilitar el soporte de CORS (Cross-Origin Resource Sharing)
 * en la aplicación web.
 *
 * Esta clase configura las políticas de CORS, permitiendo que el cliente ubicado en http://localhost:4200 pueda realizar peticiones a la
 * API del servidor sin restricciones relacionadas con el origen de la solicitud.
 */
@Configuration
public class WebConfig {

    /**
     * Método que configura las políticas de CORS para la aplicación.
     * 
     * Permite el acceso desde el origen http://localhost:4200 y
     * permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.) y Headers en las solicitudes entrantes.
     * 
     * @return una instancia de <code>WebMvcConfigurer</code> con la configuración de CORS aplicada.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200") // Permitimos el acceso SOLO desde http://localhost:4200
                        .allowedMethods("*") // Permitimos todos los metodos
                        .allowedHeaders("*"); // Permitimos todos los headers
            }
        };
    }
}
