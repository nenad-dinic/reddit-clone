package com.example.redditclone.security;

import com.example.redditclone.dto.UserDTO;
import com.example.redditclone.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    //@Value("reddit")
    private static String secret = "reddit";

    //@Value("3600") //3600 sec = 1h
    private static Long expiration = 3600l;

    public static String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = TokenUtils.getClaimsFromToken(token); // username izvlacimo iz subject polja unutar payload tokena
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(TokenUtils.secret) // izvlacenje celog payloada
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public static Date getExpirationDateFromToken(String token) {
        Date expirationDate;
        try {
            final Claims claims = TokenUtils.getClaimsFromToken(token); // username izvlacimo iz expiration time polja unutar payload tokena
            expirationDate = claims.getExpiration();
        } catch (Exception e) {
            expirationDate = null;
        }
        return expirationDate;
    }

    /*
     * Provera da li je token istekao tj da li nije prsvto expiration momenat*/
    private static boolean isTokenExpired(String token) {
        final Date expirationDate = TokenUtils.getExpirationDateFromToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    /*Provera validnosti tokena: period vazenja i provera username-a korisnika*/
    public static boolean validateToken(String token, User user) {
        final String username = getUsernameFromToken(token);
        return username.equals(user.getUsername())
                && !isTokenExpired(token);
    }

    /*Generisanje tokena za korisnika - postavljanje svih potrebnih informacija,
     * kao sto je rola korisnika.*/
    public static String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("sub", username);
        claims.put("role", role);
        claims.put("created", new Date(System.currentTimeMillis()));
        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

}

