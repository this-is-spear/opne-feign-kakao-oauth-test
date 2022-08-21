package hello.kakaoauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("kakao")
public class KakaoLoginController {

    private final Logger log = LoggerFactory.getLogger(KakaoLoginController.class);
    private final KakaoLoginService kakaoLoginService;

    public KakaoLoginController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @GetMapping("login/page")
    public void sendLoginPage() {
        kakaoLoginService.sendLoginPage();
    }

    @PostMapping("login")
    public void loginWithAccessCode(@RequestParam(required = false) AccessCode code,
                                    @RequestParam(required = false) String error,
                                    @RequestParam(required = false) String errorDescription) {
        if (code == null && error != null && !error.isBlank()) {
            log.info("Login failed cause : {}", errorDescription);
            throw new AccessCodeException("코드를 발급받는 곳에서 문제가 발생했습니다.");
        }

        log.info("Login success : {}", code);
        kakaoLoginService.loginWithAccessCode(code);
    }
}
