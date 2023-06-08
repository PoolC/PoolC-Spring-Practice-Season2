package org.poolc.controller;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.poolc.controller.session.SessionConst;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.poolc.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RoleMypageController {

    private final MemberService memberService;

    //model -> 컨트롤러에서 생성한 데이터를 담아서 view로 전달할때 사용하는 객체
    @GetMapping("/role")
    public String distributeByRole(
        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
            Member loginMember, Model model) {

        model.addAttribute("member", loginMember);
        return RoleDistributor(loginMember.getRole());
    }

    // 현재 회원들의 리스트와 / 올바른 API 검증용 로그인 사용자의 등급을 model로 전달
    @GetMapping("/admin/roleModify")
    public String showRoleOfMembers(
        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
            Member loginMember, Model model) {
        Optional<List<Member>> members = memberService.findMembers(loginMember.getRole());
        //admin validation -> findMembers return 값으로 admin 검증 실패시 홈으로 redirection(필터에 이은 2차 검증)
        if (members.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("members", members.get());
        model.addAttribute("role", loginMember.getRole());
        return "members/adminAPI/memberRoleChange";
    }


    // Query Parameter로 관리자가 변경할 회원 아이디와 등급 전달.
    @PostMapping("/admin/roleModify")
    public String acceptRoleChange(@RequestParam("memberId") String memberId,
        @RequestParam("member_role") MEMBER_ROLE member_role) {
        log.debug("memberId = " + memberId);
        log.debug("member_role = " + member_role);
        memberService.RoleModify(Long.parseLong(memberId), member_role);
        return "redirect:/";
    }

    //if-else문의 문제점 존재 -> switch 방식 or enum properties
    public String RoleDistributor(MEMBER_ROLE member_role) {
        switch (member_role) {
            case ROLE_ADMIN:
                return "members/memberRole/AdminMemberMypage";
            case ROLE_GOLD:
                return "members/memberRole/goldMemberMypage";
            case ROLE_SILVER:
                return "members/memberRole/silverMemberMypage";
            default:
                return "members/memberRole/bronzeMemberMypage";
        }
    }


}
