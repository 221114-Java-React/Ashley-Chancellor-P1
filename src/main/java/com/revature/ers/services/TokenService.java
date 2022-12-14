package com.revature.ers.services;

import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.utils.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class TokenService {
    private JwtConfig jwtConfig;

    public TokenService() {
        super();
    }

    public TokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Principal subject) {
        long now = System.currentTimeMillis();
        JwtBuilder tokenBuilder = Jwts.builder()
                .setId(subject.getUserId())
                .setIssuer("ers")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration()))
                .setSubject(subject.getUsername())
                .claim("email", subject.getEmail())
                .claim("givenName", subject.getGivenName())
                .claim("surname", subject.getSurname())
                .claim("isActive", subject.isActive())
                .claim("roleId", subject.getRoleId())
                .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return tokenBuilder.compact();
    }

    public Principal extractRequesterDetails(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
            return new Principal(claims.getId(), claims.getSubject(), claims.get("email", String.class),
                    claims.get("givenName", String.class), claims.get("surname", String.class),
                    claims.get("isActive", Boolean.class), claims.get("roleId", String.class));
        } catch(Exception e) {
            return null;
        }
    }
}
