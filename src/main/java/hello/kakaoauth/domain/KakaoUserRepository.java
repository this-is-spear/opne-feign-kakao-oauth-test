package hello.kakaoauth.domain;

import hello.kakaoauth.KakaoUser;

import java.util.Optional;

public interface KakaoUserRepository {

    Optional<KakaoUser> findByEmail(String email);

    KakaoUser save(KakaoUser member);
}
