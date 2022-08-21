package hello.kakaoauth.application;

import hello.kakaoauth.KakaoUser;
import hello.kakaoauth.domain.AccessCode;
import hello.kakaoauth.domain.AccessToken;
import hello.kakaoauth.domain.KakaoClient;
import hello.kakaoauth.domain.KakaoUserInfo;
import hello.kakaoauth.domain.KakaoUserRepository;
import org.springframework.stereotype.Service;

@Service
public class KakaoLoginService {
    private final KakaoClient kakaoLoginClient;
    private final KakaoUserRepository kakaoUserRepository;
    private final String clientId = "";

    public KakaoLoginService(KakaoClient kakaoLoginClient, KakaoUserRepository kakaoUserRepository) {
        this.kakaoLoginClient = kakaoLoginClient;
        this.kakaoUserRepository = kakaoUserRepository;
    }

    public void sendLoginPage() {
        kakaoLoginClient.sendLoginPage(clientId, "/kakao/login", "code");
    }

    public String loginWithAccessCode(AccessCode code) {
        AccessToken accessToken = kakaoLoginClient.getAccessToken(code);

        String authorizationToken = accessToken.getTokenType() + " " + accessToken.getAccessToken();

//      property_keys=["id", "kakao_account.profile.nickname","kakao_account.profile.thumbnail_image_url", "kakao_account.profile.profile_image_url","kakao_account.email"]
        KakaoUserInfo userInfo = kakaoLoginClient.getUserInfo(authorizationToken);

        KakaoUser member = kakaoUserRepository.findByEmail(userInfo.getEmail())
            .orElse(kakaoUserRepository.save(new KakaoUser(userInfo.getId(), userInfo.getNickname(), userInfo.getEmail(), userInfo.getProfileImageUrl(), userInfo.getThumbnailImageUrl())));

        member.saveAccessCode(accessToken);
        return authorizationToken;
    }
}
