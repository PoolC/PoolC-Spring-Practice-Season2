package org.poolc.controller.session;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.poolc.domain.Member;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        //request에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //session 조회
        Object result = sessionManager.getSession(request).get();
        assertThat(result).isEqualTo(member);

        //session 만료
        sessionManager.expire(request);
        assertThat(sessionManager.getSession(request)).isEqualTo(Optional.empty());
    }

    @Test
    void nullSessionTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertThat(sessionManager.getSession(request)).isEqualTo(Optional.empty());
        sessionManager.expire(request);
    }
}
