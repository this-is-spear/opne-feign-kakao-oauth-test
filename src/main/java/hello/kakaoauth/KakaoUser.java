package hello.kakaoauth;

public class KakaoUser {
    private Long id;
    private Long kakaoId;
    private String nickname;
    private String profileImageUrl;
    private String thumbnailImageUrl;
    private AccessToken accessToken;

    public KakaoUser(Long kakaoId, String nickname, String profileImageUrl, String thumbnailImageUrl) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public void saveAccessCode(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
