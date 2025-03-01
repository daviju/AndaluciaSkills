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

@Component // Anotación que indica que una clase es un "componente".
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Registramos la URL de la solicitud actual para depuración
        System.out.println("JwtFilter - Procesando request para: " + request.getRequestURI());

        // Extraemos el token del encabezado de la solicitud
        String token = getTokenFromRequest(request);

        if (token != null) {
            System.out.println("Token encontrado");

            // Verificamos si el token es válido con "isValidToken"
            if (jwtTokenProvider.isValidToken(token)) {
                
                // Extraemos el nombre de usuario del token
                String username = jwtTokenProvider.getUsernameFromToken(token);
                
                System.out.println("Token valido");

                
                // Cargamos los detalles del usuario incluyendo roles y permisos
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                System.out.println("Autoridades para el usuario" + userDetails.getAuthorities());

                
                // Creamos un objeto de autenticación para Spring Security
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                
                // Establece la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(auth);
                
                System.out.println("Token autenticado");

            } else {
                System.out.println("Token invalido");
            }
        } else {
            System.out.println("Token no encontrado");
        }

        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT del encabezado "Authorization" de la solicitud HTTP.
     * El formato esperado es "Bearer [token]".
     *
     * @param request La solicitud HTTP
     * @return El token JWT sin el prefijo "Bearer " o null si no se encuentra
     */

    private String getTokenFromRequest(HttpServletRequest request) {
    
        // Obtenemos el valor del encabezado "Authorization"
        String bearerToken = request.getHeader("Authorization");

        // Verificamos si el encabezado existe y tiene el formato correcto
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")) {
    
            // Eliminamos los primeros 7 caracteres ("Bearer ") para obtener solo el token
            return bearerToken.substring(7);
        }

        return null;
    }
}