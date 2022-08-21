# open-feign-kakao-oauth-test
카카오 인증 구현 및 테스트 with OpenFeign

## 요구사항

### 기능 요구사항

- 카카오 소셜 로그인을 이용해 토큰을 발급받습니다.
- 토큰을 이용해 사용자의 정보를 가져옵니다.
- 사용자의 정보를 인메모리 데이터베이스에 저장합니다.
- 사용자의 정보를 기반으로 토큰을 발급합니다.

### 프로그래밍 요구사항

- 소셜 API가 아닌 가짜 API를 통해 테스트할 수 있도록 구현합니다.
- 로그인부터 토큰 발급까지 테스트하기 위해 소셜 API를 직접적으로 의존하지 않도록 구현합니다.
- OpenFeign을 이용해 서버간 통신을 진행합니다.

![카카오톡 소셜 로그인 API](https://developers.kakao.com/docs/latest/ko/assets/style/images/kakaologin/kakaologin_sequence.png)

### 이벤트 흐름

#### 로그인 요청

1. 인가 코드 받기 - GET /oauth/authorize
2. 토큰 받기 - POST /oauth/token
3. 토큰을 이용해 정보 조회 - GET /v1/user/access_token_info
4. 데이터 스토어에 사용자 정보 조회 및 갱신
5. 서버 전용 토큰 발급
6. 토큰 갱신 - POST /oauth/token
7. 로그 아웃 - POST /v1/user/logout

> Host는 `https://kauth.kakao.com` 입니다.

#### 추가 항목 동의

사용자에 따라 사용자의 동의를 추가로 받는 기능이 필요하다.

### KakaoLoginClient

- 카카오 소셜 로그인 페이지로 이동합니다.
- 인가 코드를 받아 로그인을 진행합니다.
- 로그아웃합니다.
- 토큰을 갱신합니다.

### KakaoLoginService

- 인가 코드를 입력받아 카카오 소셜 로그인을 이용해 로그인합니다.
  - 인가 코드를 이용해 토큰을 발급받습니다.
  - 토큰을 이용해 사용자 정보를 조회합니다.
    - id 값으로 사용자 정보를 조회하고, 사용자 정보가 없다면 저장합니다.
- 로그아웃 합니다.
- 토큰을 갱신합니다.

### KakaoFeignClient

- 인가 코드를 받습니다.
  - 사용자가 로그인을하면 해당 요청으로 리다이렉트합니다.
- 인가 코드를 입력해 토큰을 받습니다.
  - 인가 코드가 유효하지 않으면 KakaoAccessCodeException이 발생합니다.
- 토큰으로 인증해 사용자 정보를 조회합니다.
  - 토큰이 유효하지 않으면 KakaoAccessTokenException 예외가 발생합니다.
- 로그아웃합니다.
- 토큰을 갱신합니다.

###  인가 코드 받기 - GET /oauth/authorize

#### 요청

```http request
GET /oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code HTTP/1.1
Host: kauth.kakao.com
```

#### 응답

로그인

```http request
HTTP/1.1 302 Found
Content-Length: 0
Location: ${REDIRECT_URI}?code=${AUTHORIZE_CODE}
```

로그인 취소

```http request
HTTP/1.1 302 Found
Content-Length: 0
Location: ${REDIRECT_URI}?error=access_denied&error_description=User%20denied%20access
```

### 토큰 받기 - POST /oauth/token

#### 요청

```http request
POST /oauth/token HTTP/1.1
Host: kauth.kakao.com
Content-type: application/x-www-form-urlencoded;charset=utf-8
```

```curl
curl -v -X POST "https://kauth.kakao.com/oauth/token" \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "grant_type=authorization_code" \
 -d "client_id=${REST_API_KEY}" \
 --data-urlencode "redirect_uri=${REDIRECT_URI}" \
 -d "code=${AUTHORIZE_CODE}"
 ```

#### 응답
```http request
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
    "token_type":"bearer",
    "access_token":"${ACCESS_TOKEN}",
    "expires_in":43199,
    "refresh_token":"${REFRESH_TOKEN}",
    "refresh_token_expires_in":25184000,
    "scope":"account_email profile"
}
```

### 토큰을 이용해 정보 조회 - GET /v1/user/access_token_info

#### 요청

```http request
GET /v1/user/access_token_info HTTP/1.1
Host: kapi.kakao.com
Authorization: Bearer ${ACCESS_TOKEN}
```

#### 응답

```http request
HTTP/1.1 200 OK
{
    "id":123456789,
    "expires_in": 7199,
    "app_id":1234
}
```
