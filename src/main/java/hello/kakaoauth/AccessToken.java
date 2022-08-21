package hello.kakaoauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken {
    private String tokenType;
    private String accessToken;
    private Date expiresIn;
    private String refreshToken;
    private Date refreshTokenExpiresIn;

    private AccessToken() {/*no-op*/}

    public AccessToken(String tokenType, String accessToken, Date expiresIn, String refreshToken, Date refreshTokenExpiresIn) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Date getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessToken that = (AccessToken) o;
        return Objects.equals(getTokenType(), that.getTokenType()) && Objects.equals(getAccessToken(), that.getAccessToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTokenType(), getAccessToken());
    }
}
