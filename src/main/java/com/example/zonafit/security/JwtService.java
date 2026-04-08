package com.example.zonafit.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.issuer}")
    private String issuer;

    //Convertir Base64 -> SecretKey
    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Extraer username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Extraer expiración
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Extraer cualquier claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Parsear token
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .requireIssuer(issuer)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired JWT token", e);
        }
    }

    //Validar expiración
    private Boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    //Generar token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("roles", userDetails.getAuthorities());

        return createToken(claims, userDetails.getUsername());
    }

    //Crear token
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Validar token con UserDetails
    public Boolean validateToken(String token, UserDetails userDetails) {
        final Claims claims = extractAllClaims(token);
        final String username = claims.getSubject();

        return username.equals(userDetails.getUsername()) && !isTokenExpired(claims);
    }

    //Validar token con username
    public Boolean validateToken(String token, String username) {
        final Claims claims = extractAllClaims(token);
        final String tokenUsername = claims.getSubject();

        return tokenUsername.equals(username) && !isTokenExpired(claims);
    }
}
