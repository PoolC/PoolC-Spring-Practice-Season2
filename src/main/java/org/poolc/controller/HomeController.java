package org.poolc.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.poolc.controller.session.SessionConst;
import org.poolc.controller.session.SessionManager;
import org.poolc.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SessionManager sessionManager;

    @GetMapping("/")
    public String homeLoginV2(
        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
        Model model) {

        //로그인 여부에 따라 유저에게 다른 홈화면 제공
        if (Optional.ofNullable(loginMember).isEmpty()) {
            return "home";
        }

        //세션이 유지되면 회원 홈(로그인 모드 홈)으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
