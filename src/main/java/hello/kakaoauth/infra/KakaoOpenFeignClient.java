package hello.kakaoauth.infra;

import hello.kakaoauth.domain.AccessCode;
import hello.kakaoauth.domain.AccessToken;
import hello.kakaoauth.domain.KakaoClient;
import hello.kakaoauth.domain.KakaoUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "kakao-auth", url = "")
public interface KakaoOpenFeignClient extends KakaoClient {
    @Override
    @GetMapping(
        value = "/oauth/authorize"
    )
    void sendLoginPage(@PathVariable String clientId,
                       @PathVariable String redirectUri,
                       @PathVariable String responseType);
    @Override
    @GetMapping("")
    AccessToken getAccessToken(AccessCode code);

    @Override
    @PostMapping("")
    KakaoUserInfo getUserInfo(String accessToken);
}
