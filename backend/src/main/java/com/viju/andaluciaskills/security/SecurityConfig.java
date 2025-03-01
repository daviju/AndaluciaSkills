package com.viju.andaluciaskills.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration // Clase de Configuración de seguridad
@EnableWebSecurity // Habilita la seguridad web
public class SecurityConfig {

    @Autowired // Inyección de dependencias
    private JwtFilter jwtFilter; // Filtro de JWT

    @Bean // Configuración de seguridad
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // Rutas públicas (TODOS)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/participantes/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                                "/swagger-resources/**", "/webjars/**")
                        .permitAll()

                        // Rutas de experto (SOLO ROLE_ADMIN Y ROLE_EXPERTO)
                        .requestMatchers("/api/pruebas/**").hasAnyRole("ADMIN", "EXPERTO")
                        .requestMatchers("/api/evaluaciones/**").hasAnyRole("ADMIN", "EXPERTO")
                        .requestMatchers("/api/evaluacionItem/**").hasAnyRole("ADMIN", "EXPERTO")

                        // Rutas de administrador (SOLO ROLE_ADMIN)
                        .requestMatchers("/api/especialidades/**").hasRole("ADMIN")
                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers("/api/expertos/**").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Codificador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Gestor de autenticación (para autenticar usuarios)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configuración de CORS (Cross-Origin Resource Sharing)
    // Este metodo permite el acceso del frontend (Angular)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Crea una fuente de configuración de CORS que se utilizará en la aplicación
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Crea una configuración CORS personalizada
        CorsConfiguration config = new CorsConfiguration();

        // Permite el uso de credenciales (como cookies o cabeceras de autenticación)
        config.setAllowCredentials(true);

        // Define los orígenes permitidos (dominios desde los cuales se puede acceder a los recursos)
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("http://andaluciaskills.local");

        // Permite todos los encabezados (headers) en las solicitudes entrantes
        config.addAllowedHeader("*");

        // Permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");

        // Registra la configuración de CORS para todas las rutas ("/**")
        source.registerCorsConfiguration("/**", config);

        // Devuelve la fuente de configuración de CORS
        return source;
    }

}