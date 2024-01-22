package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.example.exception.JwtTokenExpiredException;
import org.example.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && !StringUtils.isEmpty(header) && header.startsWith("Bearer")) {
            String[] splitValue = header.split(" ");
            if (splitValue.length == 2) {
                String accessToken = splitValue[1];
                String username = jwtService.extractUserName(accessToken);
                if (username != null && !StringUtils.isEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    PrincipalUser principalUser = (PrincipalUser) userAuthenticationService.loadUserByUsername(username);
                    if (jwtService.isTokenValid(accessToken)) {
                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                principalUser.getUsername(), principalUser.getPassword(), new ArrayList<>());
                        context.setAuthentication(usernamePasswordAuthenticationToken);
                        SecurityContextHolder.setContext(context);
                    }
                    throw new JwtTokenExpiredException("Token expired");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

