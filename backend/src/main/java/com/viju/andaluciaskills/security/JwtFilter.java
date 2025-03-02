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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
                    * Clase JwtFilter
 * Extraer el token JWT del encabezado de la solicitud.
 * Validar el token, verificando su firma y expiración.
 * Autenticar al usuario, estableciendo su identidad en el contexto de seguridad.
 * Permitir o denegar el acceso a recursos protegidos según los permisos del usuario.

 * Es un filtro de seguridad que garantiza que solo los usuarios con un JWT válido puedan acceder a ciertas partes de la aplicación.
*/

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        System.out.println("1. JwtFilter - Procesando request para: " + request.getRequestURI());

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
            }
        } else {
            System.out.println("INFO: No se encontró token en la request");
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT del encabezado "Authorization" de la solicitud HTTP.
     * El formato esperado es "Bearer [token]".
     *
     * @param request La solicitud HTTP
     * @return El token JWT sin el prefijo "Bearer " o null si no se encuentra
     */

    // Extrae el token del header "Authorization: Bearer <token>"
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Elimina "Bearer "
        }
        return null;
    }
}