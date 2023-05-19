package org.poolc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.poolc.controller.session.SessionConst;
import org.poolc.controller.session.SessionManager;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.poolc.service.LoginService;
import org.poolc.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class MemberControllerTest {


    @Autowired
    SessionManager sessionManager;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MemberService memberService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MockMvc mvc;

    @Test
    public void createFormGet() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/members/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/createMemberForm"));
    }

    @Test
    public void createFormPost() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/members/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/createMemberForm"));

        Member member = new Member("123", "김", "123", "123", "123",
                "123", "123", MEMBER_ROLE.ROLE_SILVER);
        memberService.join(member);

        Member member2 = new Member("123", "김", "123", "123", "123",
                "123", "123", MEMBER_ROLE.ROLE_SILVER);
        mvc.perform(MockMvcRequestBuilders.post("/members/new")
                        .flashAttr("member", member2))
                .andExpect(status().isOk())
                .andExpect(view().name("members/createMemberForm"));

        member2.setUserId("456");
        member2.setPassWord("456");
        member2.setName("성");
        mvc.perform(MockMvcRequestBuilders.post("/members/new")
                        .flashAttr("member", member2))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void MemberListGet() throws Exception {

        Member member = new Member("123", "김", "123", "123", "123",
                "123", "123", MEMBER_ROLE.ROLE_SILVER);
        memberService.join(member);
        loginService.login("123", "123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession(member, response);
        MockHttpSession session = new MockHttpSession();
        //세션에 로그인 회원 정보 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        mvc.perform(MockMvcRequestBuilders.get("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("members/memberList"));
    }

    @Test
    public void updateFormGet() throws Exception {

        Member member = new Member("123", "김", "123", "123", "123",
                "123", "123", MEMBER_ROLE.ROLE_SILVER);
        memberService.join(member);
        loginService.login("123", "123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession(member, response);
        //request에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        //세션에 로그인 회원 정보 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        mvc.perform(MockMvcRequestBuilders.get("/members/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("members/updateMemberForm"));
    }

    @Test
    @Transactional
    public void updateFormPost() throws Exception {
        Member member = new Member("123", "김", "123", "123", "123",
                "123", "123", MEMBER_ROLE.ROLE_SILVER);
        memberService.join(member);
        loginService.login("123", "123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession(member, response);
        //request에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();

        MockHttpSession session = new MockHttpSession();
        //세션에 로그인 회원 정보 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        mvc.perform(MockMvcRequestBuilders.post("/members/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("members/updateMemberForm"));


        Member member2 = new Member("1234", "김", "1234", "1234", "1234",
                "1234", "1234", MEMBER_ROLE.ROLE_SILVER);

        mvc.perform(MockMvcRequestBuilders.post("/members/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .session(session)
                        .flashAttr("member", member2))
                .andExpect(view().name("redirect:/loginedMembers/logout"));
    }
}
