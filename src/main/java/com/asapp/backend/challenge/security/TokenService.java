package com.asapp.backend.challenge.security;

import com.asapp.backend.challenge.resources.LoginResource;
import com.asapp.backend.challenge.resources.UserResource;
import com.sun.security.auth.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TokenService {

    private static final long EXPIRATION_TIME = 10 * 60 * 1000L; // 10 minutes
    private static final String ROLES = "roles";

    @Value("${SECRET_JWT}")
    private String jwtSecretKey;

    private final BlacklistedTokenRepository blacklistedTokenRepository = new BlacklistedTokenRepository();

/*    private static final long EXPIRATION_TIME = 10 * 60 * 1000l; // 10 minutes
    private static final String ROLES = "roles";
*/

    public TokenService() {
    }

    public final void removeExpired() {
        blacklistedTokenRepository.removeExpired();
    }

    public final String newToken(UserResource user) {
        DefaultClaims claims = new DefaultClaims();
        claims.setSubject(user.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }

    public final void revokeToken(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        blacklistedTokenRepository.addToken(token, expirationDate.getTime());
    }

    /**
     * throws ExpiredJwtException if token has expired
     *
     * @param token
     * @return
     */
    public final LoginResource getUserPrincipal(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
        //I'm not adding roles, I'll use just the java UserPrincipal that has a name on it, but no roles
        //List<String> roles = (List<String>) claims.get(ROLES);
        //return UserPrincipal.of(claims.getSubject(), roles.stream().map(role -> Role.valueOf(role)).collect(Collectors.toList()));
        return LoginResource.builder().username(claims.getSubject()).token(token).build();
    }

    public final boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.isTokenBlacklisted(token);
    }

    public final boolean validateToken(String token) {
        if (!isTokenBlacklisted(token)) {
            try {
                getUserPrincipal(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}