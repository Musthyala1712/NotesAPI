package org.example.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class JwtAccessToken {
    private String accessToken;
    private Date issuedAt;
    private Date expirationAt;
}

