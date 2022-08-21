package hello.kakaoauth.infra;

import hello.kakaoauth.KakaoUser;
import hello.kakaoauth.domain.KakaoUserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryKakaoUserRepository implements KakaoUserRepository {
    private static final Map<Long, KakaoUser> kakaoUserMap = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Optional<KakaoUser> findByEmail(String email) {
        return kakaoUserMap.values().stream().filter(kakaoUser -> kakaoUser.getEmail().equals(email)).findFirst();
    }

    @Override
    public KakaoUser save(KakaoUser member) {
        Long id = sequence++;
        KakaoUser kakaoUser = new KakaoUser(id, member.getKakaoId(), member.getEmail(), member.getNickname(), member.getProfileImageUrl(), member.getThumbnailImageUrl(), null);
        return kakaoUserMap.put(id, kakaoUser);
    }
}
