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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class RoleMypageControllerTest {


    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MemberService memberService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private MockMvc mvc;

    @Test
    public void distributeByRoleTest() throws Exception {

        Member member = new Member("123", "김", "123", "123", "123",
                "123", "123", MEMBER_ROLE.ROLE_BRONZE);
        memberService.join(member);
        loginService.login("123", "123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession(member, response);
        //request에 응답 쿠키 저장
        MockHttpSession session = new MockHttpSession();
        //세션에 로그인 회원 정보 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        mvc.perform(MockMvcRequestBuilders.get("/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("members/memberRole/bronzeMemberMypage"));

        member.setRole(MEMBER_ROLE.ROLE_SILVER);
        mvc.perform(MockMvcRequestBuilders.get("/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("members/memberRole/silverMemberMypage"));

        member.setRole(MEMBER_ROLE.ROLE_GOLD);
        mvc.perform(MockMvcRequestBuilders.get("/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("members/memberRole/goldMemberMypage"));

        member.setRole(MEMBER_ROLE.ROLE_ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("members/memberRole/AdminMemberMypage"));

    }

    @Test
    public void roleModifyGet() throws Exception {

        Member member = new Member("123", "김", "123", "123", "123",
                "123", "123", MEMBER_ROLE.ROLE_ADMIN);
        memberService.join(member);
        loginService.login("123", "123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession(member, response);
        //request에 응답 쿠키 저장
        MockHttpSession session = new MockHttpSession();
        //세션에 로그인 회원 정보 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        mvc.perform(MockMvcRequestBuilders.get("/admin/roleModify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("members/adminAPI/memberRoleChange"));
    }

    @Test
    public void roleModifyPost() throws Exception {

        Member member = new Member("123", "김", "123", "123", "123",
                "123", "123", MEMBER_ROLE.ROLE_ADMIN);
        memberService.join(member);

        Member user = new Member("567", "박", "567", "567", "567",
                "567", "567", MEMBER_ROLE.ROLE_BRONZE);
        memberService.join(user);

        loginService.login("123", "123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.createSession(member, response);
        //request에 응답 쿠키 저장
        MockHttpSession session = new MockHttpSession();
        //세션에 로그인 회원 정보 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);


        mvc.perform(MockMvcRequestBuilders.post("/admin/roleModify")
                        .param("memberId", user.getId().toString())
                        .param("member_role", MEMBER_ROLE.ROLE_SILVER.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member))
                        .session(session))
                .andExpect(view().name("redirect:/"));
        assertThat(user.getRole()).isEqualTo(MEMBER_ROLE.ROLE_SILVER);
    }

}