package org.poolc.controller.session;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.poolc.domain.Member;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest(){

        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        //request에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //session 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        //session 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isEqualTo(null);
    }

    @Test
    void nullSessionTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertThat(sessionManager.getSession(request)).isNull();
        sessionManager.expire(request);
    }
}
