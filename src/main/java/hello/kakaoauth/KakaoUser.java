package hello.kakaoauth;

import hello.kakaoauth.domain.AccessToken;

public class KakaoUser {
    private Long id;
    private Long kakaoId;
    private String nickname;
    private String profileImageUrl;
    private String thumbnailImageUrl;
    private String email;
    private AccessToken accessToken;

    public KakaoUser(Long id, Long kakaoId, String email, String nickname, String profileImageUrl, String thumbnailImageUrl, AccessToken accessToken) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.accessToken = accessToken;
    }

    public KakaoUser(Long kakaoId, String nickname, String email, String profileImageUrl, String thumbnailImageUrl) {
        this(null, kakaoId, nickname, profileImageUrl, email, thumbnailImageUrl, null);
    }

    public void saveAccessCode(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public Long getKakaoId() {
        return kakaoId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public String getEmail() {
        return email;
    }
}
