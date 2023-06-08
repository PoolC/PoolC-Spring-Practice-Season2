package org.poolc.controller;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.poolc.controller.form.LoginFormController;
import org.poolc.controller.session.SessionConst;
import org.poolc.domain.Member;
import org.poolc.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginFormController form) {
        return "members/loginMemberForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginFormController form,
        BindingResult bindingResult,
        @RequestParam(defaultValue = "/") String redirectURL,
        HttpServletRequest request) {
        // valid한 입력이 아닐경우(채우지 않은 칸이 존재), 로그인 화면으로 다시 redirection
        if (bindingResult.hasErrors()) {
            return "members/loginMemberForm";
        }
        Optional<Member> loginMember = loginService.login(form.getLoginId(), form.getPassword());
        // 가입하지 않은 아이디일 경우, 로그인 화면으로 다시 redirection
        if (loginMember.isEmpty()) {
            return "members/loginMemberForm";
        }
        //로그인 성공 -> 세션이 있으면 해당 세션 반환, 없으면 신규세션 생성(default=true) false면 세션 없으면 null을 반환
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.get());

        return "redirect:" + redirectURL;
    }

    // 로그아웃 page로 이동 -> "/logout"시 query parameter(/login?logout)로 redirect 문제 차후 (Get, POST)분리
    @RequestMapping(value = "/loginedMembers/logout", method = {RequestMethod.GET,
        RequestMethod.POST})
    public String logout(HttpServletRequest request) {
        //로그아웃시 해당 세션 삭제 후 홈으로 이동
        sessionDelete(request);
        return "redirect:/";
    }

    public void sessionDelete(HttpServletRequest request) {
        Optional<HttpSession> session = Optional.ofNullable(request.getSession(false));
        if (!session.isEmpty()) {
            session.get().invalidate();
        }
    }


}
