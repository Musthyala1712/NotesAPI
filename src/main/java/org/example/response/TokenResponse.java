package org.example.response;

import lombok.Getter;
import lombok.Setter;
import org.example.model.User;

@Getter
@Setter
public class TokenResponse extends JwtAccessToken {
    private User user;

    public TokenResponse(User user, JwtAccessToken jwtAccessToken) {
        super(jwtAccessToken.getAccessToken(), jwtAccessToken.getIssuedAt(), jwtAccessToken.getExpirationAt());
        this.user = user;
    }
}
