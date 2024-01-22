package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.model.PrincipalUser;
import org.example.response.JwtAccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.signing.key}")
    private String jwtSigningKey;

    @Override
    public JwtAccessToken generateToken(PrincipalUser principalUser) {
        return generateToken(new HashMap<>(), principalUser);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.after(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private JwtAccessToken generateToken(Map<String, Object> extraClaims, PrincipalUser principalUser) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expirationAt = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2);
        String accessToken = Jwts.builder().claims(extraClaims).subject(principalUser.getUsername())
                .issuedAt(issuedAt)
                .expiration(expirationAt)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
        return new JwtAccessToken(accessToken, issuedAt, expirationAt);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
