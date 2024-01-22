package org.example.security;

import org.example.model.PrincipalUser;
import org.example.response.JwtAccessToken;

public interface JwtService {
    JwtAccessToken generateToken(PrincipalUser principalUser);

    String extractUserName(String token);

    boolean isTokenValid(String token);
}
