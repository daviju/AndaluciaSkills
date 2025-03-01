package com.viju.andaluciaskills.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component // Marca la clase como un componente de Spring
public class JwtTokenProvider { // Clase que se encarga de generar y validar tokens JWT

    // Se inyecta el secreto utilizado para firmar los JWT y la duración en segundos del token desde el archivo de configuración
    @Value("${app.security.jwt.secret}")
    private String jwtSecret;

    @Value("${app.security.jwt.expiration}")
    private Long jwtDuracionSegundos;

    /**
     * Genera un token JWT para un usuario autenticado.
     *
     * @param authentication Objeto de autenticación que contiene los detalles del usuario
     * @return El token JWT generado
     */
    public String generateToken(Authentication authentication) {
       
        // Obtiene los detalles del usuario desde el objeto de autenticación
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Construye el token JWT utilizando los detalles del usuario y configuraciones (secreto y duración)
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // El nombre de usuario se establece como el sujeto del token
                .setIssuedAt(new Date()) // Fecha de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + (jwtDuracionSegundos * 1000))) // Fecha de expiración del token (actual + duración en segundos)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())) // Firma el token con la clave secreta
                .compact(); // Devuelve el token compactado
    }

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     *
     * @param token El token JWT
     * @return El nombre de usuario (subject) extraído del token
     */
    public String getUsernameFromToken(String token) {
        
        // Parseamos el token para obtener los claims (información interna del token)
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes())) // Usamos la misma clave secreta para verificar la firma
                .build()
                .parseClaimsJws(token) // Parseamos el token JWT
                .getBody(); // Obtenemos el cuerpo (claims) del token

        // Devolvemos el nombre de usuario (subject) del token
        return claims.getSubject();
    }

    /**
     * Verifica si el token JWT es válido.
     * 
     * Un token es válido si se puede parsear correctamente sin errores, si la firma es correcta
     * y si no está expirado.
     *
     * @param token El token JWT
     * @return true si el token es válido, false en caso contrario
     */
    public boolean isValidToken(String token) {
        try {
            // Intentamos parsear el token utilizando la clave secreta
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes())) // Verificamos la firma usando la misma clave secreta
                .build()
                .parseClaimsJws(token); // Si el parsing es exitoso, el token es válido
            return true;
        } catch (JwtException e) {
            // Si ocurre alguna excepción (como firma inválida o expiración), el token no es válido
            return false;
        }
    }
}
