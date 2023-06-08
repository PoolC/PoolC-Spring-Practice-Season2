package org.poolc.controller.session;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.CookieGenerator;

@Component
public class SessionManager {

    private static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response) {

        //세션 id 생성 후 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        //    Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);

        CookieGenerator cg = new CookieGenerator();
        cg.setCookieName(SESSION_COOKIE_NAME);
        cg.addCookie(response, sessionId);

//        mySessionCookie.setPath("/");
//        response.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회 ->request로부터 온 Session에 저장해둔 memberID가 있는지 체크/ 있다면 value return
     */
    public Optional<Object> getSession(HttpServletRequest request) {
        if (findCookie(request, SESSION_COOKIE_NAME).isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(
            sessionStore.get(findCookie(request, SESSION_COOKIE_NAME).get().getValue()));
    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request) {
        if (!findCookie(request, SESSION_COOKIE_NAME).isEmpty()) {
            sessionStore.remove(findCookie(request, SESSION_COOKIE_NAME).get().getValue());
        }

    }

    private Optional<Cookie> findCookie(HttpServletRequest request, String cookieName) {
        return request.getCookies() == null ? Optional.empty() : Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals(cookieName))
            .findAny();
    }
}
