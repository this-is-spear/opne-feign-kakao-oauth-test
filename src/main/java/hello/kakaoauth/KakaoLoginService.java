package hello.kakaoauth;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KakaoLoginService {
    private final KakaoLoginClient kakaoLoginClient;
    private final KakaoUserRepository kakaoUserRepository;

    public KakaoLoginService(KakaoLoginClient kakaoLoginClient, KakaoUserRepository kakaoUserRepository) {
        this.kakaoLoginClient = kakaoLoginClient;
        this.kakaoUserRepository = kakaoUserRepository;
    }

    public void sendLoginPage() {
        kakaoLoginClient.sendLoginPage();
    }

    public String loginWithAccessCode(AccessCode code) {
        AccessToken accessToken = kakaoLoginClient.getAccessToken(code);

        String authorizationToken = accessToken.getTokenType() + " " + accessToken.getAccessToken();

//      property_keys=["id", "kakao_account.profile.nickname","kakao_account.profile.thumbnail_image_url", "kakao_account.profile.profile_image_url","kakao_account.email"]
        KakaoUserInfo userInfo = kakaoLoginClient.getUserInfo(authorizationToken);

        KakaoUser member = kakaoUserRepository.findByEmail(userInfo.getEmail())
            .orElse(kakaoUserRepository.save(new KakaoUser(userInfo.getId(), userInfo.getNickname(), userInfo.getProfileImageUrl(), userInfo.getThumbnailImageUrl())));

        member.saveAccessCode(accessToken);
        return authorizationToken;
    }
}
