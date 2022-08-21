package hello.kakaoauth.domain;

import org.springframework.web.bind.annotation.PathVariable;

public interface KakaoClient {
    void sendLoginPage(String clientId, String redirectUri, String responseType);

    AccessToken getAccessToken(AccessCode code);

    KakaoUserInfo getUserInfo(String accessToken);
}
