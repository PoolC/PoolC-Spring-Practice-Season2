package org.poolc.controller;

import lombok.RequiredArgsConstructor;
import org.poolc.controller.form.LoginFormController;
import org.poolc.controller.session.SessionConst;
import org.poolc.controller.session.SessionManager;
import org.poolc.domain.Member;
import org.poolc.service.LoginServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceImpl loginService;

    // 로그인 페이지로 이동
    @GetMapping("/members/login")
    public String loginForm(@ModelAttribute("loginForm") LoginFormController form){
        return "members/loginMemberForm";
    }

    @PostMapping("/members/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginFormController form, BindingResult bindingResult, HttpServletRequest request){
        // valid한 입력이 아닐경우(채우지 않은 칸이 존재), 로그인 화면으로 다시 redirection
        if(bindingResult.hasErrors()){
            return "members/loginMemberForm";
        }
        // 가입하지 않은 아이디일 경우, 로그인 화면으로 다시 redirection
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if(loginMember == null){
            return "members/loginMemberForm";
        }

        //로그인 성공 -> 세션이 있으면 해당 세션 반환, 없으면 신규세션 생성(default=true) false면 세션 없으면 null을 반환
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    // 로그아웃 page로 이동
    @RequestMapping(value="/members/logout" , method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(HttpServletRequest request){
        //로그아웃시 해당 세션 삭제 후 홈으로 이동
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }



}
