package org.poolc.controller;

import lombok.RequiredArgsConstructor;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoleMypageController {

    private final MemberService memberService;

    //model -> 컨트롤러에서 생성한 데이터를 담아서 view로 전달할때 사용하는 객체
    @GetMapping("/role")
    public String distributeByRole(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                           Member loginMember, Model model) {

        model.addAttribute("member", loginMember);
        return RoleDistributor(loginMember.getRole());
    }

    // 현재 회원들의 리스트와 / 올바른 API 검증용 로그인 사용자의 등급을 model로 전달
    @GetMapping("/admin/roleModify")
    public String showRoleOfMembers(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                            Member loginMember, Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        model.addAttribute("role", loginMember.getRole());
        return "members/adminAPI/memberRoleChange";
    }


    // Query Parameter로 관리자가 변경할 회원 아이디와 등급 전달.
    @PostMapping("/admin/roleModify")
    public String acceptRoleChange(@RequestParam("memberId") String memberId,
                                   @RequestParam("member_role") MEMBER_ROLE member_role) {
        System.out.println("memberId = " + memberId);
        System.out.println("member_role = " + member_role);
        memberService.RoleModify(Long.parseLong(memberId), member_role);
        return "redirect:/";
    }

    //if-else문의 문제점 존재 -> 더 나은 방식 없을까?
    public String RoleDistributor(MEMBER_ROLE member_role) {
        if (member_role == MEMBER_ROLE.ROLE_ADMIN) {
            return "members/memberRole/AdminMemberMypage";
        } else if (member_role == MEMBER_ROLE.ROLE_GOLD) {
            return "members/memberRole/goldMemberMypage";
        } else if (member_role == MEMBER_ROLE.ROLE_SILVER) {
            return "members/memberRole/silverMemberMypage";
        } else {
            return "members/memberRole/bronzeMemberMypage";
        }
    }


}
