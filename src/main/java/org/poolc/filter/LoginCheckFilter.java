package org.poolc.filter;

import lombok.extern.slf4j.Slf4j;
import org.poolc.controller.session.SessionConst;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/", "/members/new", "/login",
            "/loginedMembers/logout", "/css/*"};
    private static final String[] adminlist = {"/admin/**"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            log.info("인증 체크 필터 시작 {}", requestURI);
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null ||
                        session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    //로그인으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" +
                            requestURI);
                    return; //non-authentication 사용자는 다음으로 진행하지 않고 끝!
                }
                // 관리자 페이지면 권한 확인
                log.info("관리자 인증 체크 필터 시작 {}", requestURI);
                if (isAdminCheckPath(requestURI)) {
                    Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
                    if (member.getRole() != MEMBER_ROLE.ROLE_ADMIN) {
                        log.info("관리자 인증 실패 {}", requestURI);
                        httpResponse.sendRedirect("/");
                        return;
                    }
                    log.info("관리자 인증 체크 필터 종료 {}", requestURI);
                    //관리자 성공
                }

            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; //Exception logging 가능 하지만, Tomcat까지 Exception를 보내주어야 함
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    //whitelist Path들은 authentication check 면제(return == false 경우).
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    //admin Path들은 authentication check 관리자 권한 체크.
    private boolean isAdminCheckPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(adminlist, requestURI);
    }


}
