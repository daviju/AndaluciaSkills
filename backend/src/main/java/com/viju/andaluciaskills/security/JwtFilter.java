package com.viju.andaluciaskills.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/*
 * Clase JwtFilter
 * Extraer el token JWT del encabezado de la solicitud.
 * Validar el token, verificando su firma y expiración.
 * Autenticar al usuario, estableciendo su identidad en el contexto de seguridad.
 * Permitir o denegar el acceso a recursos protegidos según los permisos del usuario.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    // Lista de rutas públicas que no necesitan autenticación
    private final List<String> publicPaths = Arrays.asList(
        "/api/auth/**",
        // Rutas específicas de participantes que deben ser públicas
        "/api/participantes",
        "/api/participantes/buscarparticipante/**",
        // No incluyas /api/participantes/buscarparticipantesespecialidad/** aquí
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // Verificar si la ruta actual coincide con alguna ruta pública
        boolean isPublicPath = publicPaths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
        
        System.out.println("Checking path: " + path + ", isPublicPath: " + isPublicPath);
        return isPublicPath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        // Si es una ruta pública, permitir el acceso sin token
        if (shouldNotFilter(request)) {
            System.out.println("Ruta pública detectada, permitiendo acceso sin token: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("1. JwtFilter - Procesando request para: " + request.getRequestURI());
        System.out.println("Method: " + request.getMethod());
        System.out.println("Headers: ");
        request.getHeaderNames().asIterator()
            .forEachRemaining(headerName -> 
                System.out.println(headerName + ": " + request.getHeader(headerName)));

        try {
            String token = extractToken(request);
            if (token != null) {
                System.out.println("2. Token encontrado en request");

                if (tokenProvider.isValidToken(token)) {
                    String username = tokenProvider.getUsernameFromToken(token);
                    System.out.println("3. Token válido para usuario: " + username);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    System.out.println("4. Autoridades del usuario: " + userDetails.getAuthorities());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("5. Autenticación establecida en SecurityContext");
                } else {
                    System.out.println("ERROR: Token inválido");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } else {
                System.out.println("INFO: No se encontró token en la request");
                if (!shouldNotFilter(request)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: Exception durante el procesamiento del token: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}