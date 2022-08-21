package hello.kakaoauth;

import java.util.Objects;

public class AccessCode {
    String accessCode;

    private AccessCode() {/*no-op*/}

    public AccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getAccessCode() {
        return accessCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessCode that = (AccessCode) o;
        return Objects.equals(getAccessCode(), that.getAccessCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccessCode());
    }
}
