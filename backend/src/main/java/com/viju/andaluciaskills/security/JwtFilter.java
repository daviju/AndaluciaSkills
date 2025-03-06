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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    // Utilizamos AntPathMatcher para comparar rutas con patrones flexibles (*, **, ?)
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    // Lista de rutas públicas que no necesitan autenticación
    private final List<String> publicPaths = Arrays.asList(
            "/api/auth/**",
            // Rutas específicas de participantes que deben ser públicas
            "/api/participantes",
            "/api/participantes/buscarparticipante/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // Para las rutas de buscarparticipantesespecialidad debemos procesar el token
        if (path.contains("/api/participantes/buscarparticipantesespecialidad")) {
            System.out.println("Ruta protegida detectada, REQUIERE AUTENTICACIÓN: " + path);
            return false; // No omitir el filtro, procesamos el token
        }

        // Para las demás rutas, verificamos si coinciden con patrones públicos
        boolean isPublicPath = publicPaths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

        System.out.println("Checking path: " + path + ", isPublicPath: " + isPublicPath);

        return isPublicPath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Si es una ruta pública, permitimos el acceso sin token
        if (shouldNotFilter(request)) {
            System.out.println("Ruta pública detectada, permitiendo acceso sin token: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        // Logs para debugear el filtro
        System.out.println("JwtFilter -> Procesando request para: " + request.getRequestURI());
        System.out.println("Method: " + request.getMethod());
        System.out.println("Headers: ");

        // Mostramos TODOS los HEADERS de la solicitud (Content-Type y Authorization)
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + ": " + request.getHeader(headerName)));

        try {
            String token = extractToken(request);
            
            if (token != null) {
                System.out.println("Token encontrado en request");
                System.out.println("Token recibido: " + token.substring(0, Math.min(token.length(), 20)) + "...");

                if (tokenProvider.isValidToken(token)) { // Si el token es valido, entonces
                    String username = tokenProvider.getUsernameFromToken(token); // extraemos el username
                    System.out.println("Token válido para usuario: " + username);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username); // cargamos los detalles del usuario
                    System.out.println("Autoridades del usuario: " + userDetails.getAuthorities());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( // autenticamos al usuario
                            userDetails, null, userDetails.getAuthorities()); // asignamos las autoridades del usuario
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Con esto le decimos a la aplicación que el usuario está autenticado
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    System.out.println("Autenticación establecida en SecurityContext");

                } else {
                    System.out.println("ERROR: Token inválido");
                    
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // si el token no es válido, devolvemos un error 401
                    
                    return;
                }
            } else {
                System.out.println("ERROR: No se encontró token en la request");

                if (!shouldNotFilter(request)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // si no hay token y la ruta no es pública, devolvemos un error 401
                    
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: Exception durante el procesamiento del token: " + e.getMessage());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // si ocurre una excepción, devolvemos un error 401

            return;
        }

        filterChain.doFilter(request, response); 
    }


    // METODO PARA EXTRAER EL TOKEN DEL HEADER DE LA SOLICITUD
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // Nos aseguramos de que el bearerToken sea valido y comience con "Bearer"
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}