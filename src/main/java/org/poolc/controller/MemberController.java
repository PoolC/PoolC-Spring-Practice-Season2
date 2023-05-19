package org.poolc.controller;

import lombok.RequiredArgsConstructor;
import org.poolc.controller.session.SessionConst;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.poolc.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;


    //회원 가입 페이지
    @GetMapping("/new")
    public String createForm(@ModelAttribute("member") Member member) {
        return "members/createMemberForm";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("member") Member member, BindingResult bindingResult) {

        // 회원가입 정보가 valid하지않은 경우(채우지 않은 칸 존재) -> 다시 회원 가입 창으로 redirection
        if (bindingResult.hasErrors()) {
            return "members/createMemberForm";
        }
        //기존에 있는 회원 아이디와 동이한지 체크
        if (memberService.findByUserId(member.getUserId()).isPresent()) {
            return "members/createMemberForm";
        }
        // 신규회원의 등급은 브론즈로 설정
        member.setRole(MEMBER_ROLE.ROLE_BRONZE);
        memberService.join(member);

        return "redirect:/";
    }

    // 회원들 리스트 나열
    @GetMapping("")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    // 회원정보 수정 창
    @GetMapping("/update")
    public String UpdateForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                     Member loginMember, Model model) {
        //회원 업데이트 화면으로 이동
        model.addAttribute("member", loginMember);
        return "members/updateMemberForm";
    }


    @PostMapping("/update")
    public String update(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                         @Valid @ModelAttribute("member") Member member, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "members/updateMemberForm";
        }
        memberService.update(loginMember.getId(), member);

        return "redirect:/loginedMembers/logout";
    }


}
