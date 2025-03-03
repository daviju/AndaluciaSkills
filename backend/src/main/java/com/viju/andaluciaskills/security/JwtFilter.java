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

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean isParticipantesPath = pathMatcher.match("/api/participantes/**", path);
        System.out.println("Checking path: " + path + ", isParticipantesPath: " + isParticipantesPath);
        return isParticipantesPath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        System.out.println("1. JwtFilter - Procesando request para: " + request.getRequestURI());
        System.out.println("Method: " + request.getMethod());
        System.out.println("Headers: ");
        request.getHeaderNames().asIterator()
            .forEachRemaining(headerName -> 
                System.out.println(headerName + ": " + request.getHeader(headerName)));

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

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}