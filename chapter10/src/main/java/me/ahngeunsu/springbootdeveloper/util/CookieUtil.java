package me.ahngeunsu.springbootdeveloper.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtil {
    // 요청값(이름, 값, 만료기간)을 바탕으로 쿠키 추가
    public static void addCookie(HttpServletResponse response,
                            String name,
                            String value,
                            int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키의 이름을 입력 받아서 쿠키 삭제
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return;             // 메서드 종료
        }

        for(Cookie cookie : cookies) {
            if(name.equals(cookie.getName())){
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
    /*
        deleteCookie() : 쿠키 이름을 입력 받아서 쿠키를 삭제
            -> 실제로 삭제하는 방법은 없으므로 파라미터로 넘어온 키의 쿠키를 빈값으로 바꾸고 만료시간을 0 으로 설정해서
            쿠키가 재성성 되자 마자 만료처리를 함으로써 구성

            20250120에 객체를 직렬화 / 역직렬화하는 메서드를 구현할 예정
     */
    // 객체를 직렬화 해서 쿠키의 값으로 변환
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    // 쿠키르 역직렬화 해서 객체로 변환
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getDecoder().decode(cookie.getValue())
                )
        );
    }
}
/*
    사용자 정보를 조회해 users 테이블에 사용자 정보가 있다면 리소스 서버(OAuth2 관련)에서 제공해주는
    이름을 업데이트합니다.

    사용자 정보가 없다면 새 사용자를 생성해서 DB 에 저장할 수 있도록 하겠습니다.

    먼저, domain 의 User.java 수정
 */
