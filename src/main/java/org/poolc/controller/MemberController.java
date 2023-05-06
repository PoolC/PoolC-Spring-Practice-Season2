package org.poolc.controller;

import com.sun.xml.bind.v2.TODO;
import org.poolc.controller.session.SessionConst;
import org.poolc.domain.Member;
import org.poolc.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //회원 가입 페이지
    @GetMapping("/members/new")
    public String createForm(@ModelAttribute("member") Member member){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")

    public String create(@Valid @ModelAttribute("member") Member member, BindingResult bindingResult){
        // 회원가입 정보가 valid하지않은 경우(채우지 않은 칸 존재) -> 다시 회원 가입 창으로 redirection
        if(bindingResult.hasErrors()){
            return "members/createMemberForm";
        }

        //기존에 있는 회원 아이디와 동이한지 체크
        if(memberService.findByUserId(member.getUserId()).isPresent()){
            return "members/createMemberForm";
        }

        memberService.join(member);

        return "redirect:/";
    }

    // 회원들 리스트 나열
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    // 회원정보 수정 창 -> 회원 전용 홈(후술 로그인 홈)에서만 접근 가능해야함
    @GetMapping("/members/update")
    public String UpdateForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                         Member loginMember, Model model){
        //세션에 회원데이터가 없으면 home
        if(loginMember ==null){
            return "home";
        }

        //세션이 유지되면(로그인된 상태면) 회원 업데이트 화면으로 이동
        model.addAttribute("member", loginMember);
        return "members/updateMemberForm";
    }


    @PostMapping("/members/update")
    public String update(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                         @Valid @ModelAttribute("member") Member member, BindingResult bindingResult){
        //세션에 회원데이터가 없으면 home ->의도적 API접근 막기 위함
        if(loginMember ==null){
            return "home";
        }
        if(bindingResult.hasErrors()){
            return "members/updateMemberForm";
        }
        // 업데이트
        memberService.update(loginMember.getId(), member);

        return "redirect:/members/logout";
    }



}
