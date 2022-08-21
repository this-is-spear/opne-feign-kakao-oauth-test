package hello.kakaoauth.application;

import hello.kakaoauth.KakaoUser;
import hello.kakaoauth.domain.AccessCode;
import hello.kakaoauth.domain.AccessToken;
import hello.kakaoauth.domain.KakaoClient;
import hello.kakaoauth.domain.KakaoUserInfo;
import hello.kakaoauth.domain.KakaoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KakaoLoginServiceTest {

    @Mock
    KakaoClient kakaoLoginClient;

    @Mock
    KakaoUserRepository kakaoUserRepository;
    KakaoLoginService kakaoLoginService;

    @BeforeEach
    void setUp() {
        kakaoLoginService = new KakaoLoginService(kakaoLoginClient, kakaoUserRepository);
    }

    @Test
    @DisplayName("로그인 페이지로 이동합니다.")
    void sendLoginPage() {
        doNothing().when(kakaoLoginClient).sendLoginPage(any(), any(), any());

        kakaoLoginService.sendLoginPage();

        verify(kakaoLoginClient, atLeastOnce()).sendLoginPage(any(), any(), any());
    }

    @Test
    @DisplayName("인가 코드를 받아 회원가입을 진행합니다.")
    void loginWithAccessCode_Register() {
        String tokenType = "Bearer";
        String token = "accessToken";
        String authorizationToken = tokenType + " " + token;

        AccessToken accessToken = new AccessToken(tokenType, token, new Date(), "refreshToken", new Date());
        when(kakaoLoginClient.getAccessToken(new AccessCode("code"))).thenReturn(
            accessToken
        );

        when(kakaoLoginClient.getUserInfo(authorizationToken)).thenReturn(
            new KakaoUserInfo(1234L, "this", "/url", "/url", "email@email.com")
        );

        when(kakaoUserRepository.findByEmail("email@email.com")).thenReturn(Optional.empty());
        when(kakaoUserRepository.save(any())).thenReturn(new KakaoUser(1234L, "this", "", "/url", "url"));

        kakaoLoginService.loginWithAccessCode(new AccessCode("code"));
    }

    @Test
    @DisplayName("인가 코드를 받아 로그인을 진행합니다.")
    void loginWithAccessCode_Login() {
        String tokenType = "Bearer";
        String token = "accessToken";
        String authorizationToken = tokenType + " " + token;

        AccessToken accessToken = new AccessToken(tokenType, token, new Date(), "refreshToken", new Date());
        when(kakaoLoginClient.getAccessToken(new AccessCode("code"))).thenReturn(
            accessToken
        );

        when(kakaoLoginClient.getUserInfo(authorizationToken)).thenReturn(
            new KakaoUserInfo(1234L, "this", "/url", "/url", "email@email.com")
        );

        when(kakaoUserRepository.findByEmail("email@email.com"))
            .thenReturn(Optional.of(new KakaoUser(1234L, "this", "email@email.com", "/url", "url")));

        kakaoLoginService.loginWithAccessCode(new AccessCode("code"));
    }
}
