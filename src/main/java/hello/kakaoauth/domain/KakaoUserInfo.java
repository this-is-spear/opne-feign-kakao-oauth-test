package hello.kakaoauth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo {
    //property_keys=["id", "kakao_account.profile.nickname","kakao_account.profile.thumbnail_image_url", "kakao_account.profile.profile_image_url","kakao_account.email"]
    private Long id;
    private String nickname;
    private String thumbnailImageUrl;
    private String profileImageUrl;
    private String email;

    private KakaoUserInfo() {
    }

    public KakaoUserInfo(Long id, String nickname, String thumbnailImageUrl, String profileImageUrl, String email) {
        this.id = id;
        this.nickname = nickname;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getEmail() {
        return email;
    }
}
