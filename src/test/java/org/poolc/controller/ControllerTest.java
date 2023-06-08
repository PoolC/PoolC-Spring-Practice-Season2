package org.poolc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.poolc.controller.form.LoginFormController;
import org.poolc.controller.session.SessionConst;
import org.poolc.controller.session.SessionManager;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.poolc.service.LoginService;
import org.poolc.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

//@WebMvcTest(HomeController.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class ControllerTest {


    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MockMvc mvc;


    @Test
    public void home() throws Exception {

        // perform() - 요청을 전송하는 역할, 결과로 ResultActions객체를 받고, 이 객체에서 리턴값을 검증하고 확인할 수 있는 andExcpect()메소드를 제공함.
        mvc.perform(MockMvcRequestBuilders.get("/"))
            //상태코드 -> isOk()는 상태코드 200
            .andExpect(status().isOk())
            // 응답본문의 내용을 검증함 -> Controller에서 'home'을 리턴하기 때문에 이값이 맞는지 검증한다.
            .andExpect(view().name("home"));
    }

    @Test
    public void loginHome() throws Exception {

        Member member = new Member("1234", "김", "1234", "1234", "1234",
            "1234", "1234", MEMBER_ROLE.ROLE_SILVER);
        memberService.join(member);
        loginService.login("123", "123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession(member, response);
        //request에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        //세션에 로그인 회원 정보 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        mvc.perform(MockMvcRequestBuilders.get("/")
                .session(session))
            .andExpect(status().isOk())
            .andExpect(view().name("loginHome"));
    }

    @Test
    public void loginGet() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("members/loginMemberForm"));
    }

    @Test
    public void loginPost() throws Exception {
        Member member = new Member("1234", "김", "1234", "1234", "1234",
            "1234", "1234", MEMBER_ROLE.ROLE_SILVER);
        memberService.join(member);
        LoginFormController form = new LoginFormController();
        form.setLoginId("12345");
        form.setPassword("12345");
        mvc.perform(post("/login"))
            .andExpect(status().isOk())
            //나중에 변경
            .andExpect(view().name("members/loginMemberForm"));

        mvc.perform(post("/login")
                .flashAttr("loginForm", form))
            .andExpect(status().isOk())
            .andExpect(view().name("members/loginMemberForm"));
        form.setLoginId("1234");
        form.setPassword("1234");
        mvc.perform(post("/login")
                .param("redirectURL", "/")
                .flashAttr("loginForm", form))
            .andExpect(view().name("redirect:/"));
    }

    @Test
    public void logoutGetAndPost() throws Exception {
        Member member = new Member("1234", "김", "1234", "1234", "1234",
            "1234", "1234", MEMBER_ROLE.ROLE_SILVER);
        memberService.join(member);
        loginService.login("123", "123");

        mvc.perform(MockMvcRequestBuilders.get("/loginedMembers/logout"))
            .andExpect(view().name("redirect:/"));

        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession(member, response);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        mvc.perform(post("/loginedMembers/logout")
                .session(session))
            .andExpect(view().name("redirect:/"));
    }

}
