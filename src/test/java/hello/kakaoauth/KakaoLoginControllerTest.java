package hello.kakaoauth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KakaoLoginControllerTest {
    @Mock
    KakaoLoginService kakaoLoginService;
    KakaoLoginController kakaoLoginController;

    @BeforeEach
    void setUp() {
        kakaoLoginController = new KakaoLoginController(kakaoLoginService);
    }

    @Test
    @DisplayName("로그인 페이지로 이동합니다.")
    void sendLoginPage() {
        doNothing().when(kakaoLoginService).sendLoginPage();

        kakaoLoginController.sendLoginPage();

        verify(kakaoLoginService, atLeastOnce()).sendLoginPage();
    }

    @Test
    @DisplayName("인가 코드를 이용해 로그인을 진행합니다.")
    void loginWithAccessCode() {
        doNothing().when(kakaoLoginService).loginWithAccessCode(any());

        kakaoLoginController.loginWithAccessCode(new AccessCode("thisiscode"), null, null);

        verify(kakaoLoginService, atLeastOnce()).loginWithAccessCode(new AccessCode("thisiscode"));
    }

    @Test
    @DisplayName("인증에 실패하여 에러가 발생했을 경우 예외를 발생시킵니다.")
    void loginWithAccessCode_Failed() {
        Assertions.assertThatThrownBy(
            () -> kakaoLoginController.loginWithAccessCode(null, "KEO200", "something worngs")
        ).isInstanceOf(AccessCodeException.class);
    }
}
