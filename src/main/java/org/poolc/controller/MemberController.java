package org.poolc.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.poolc.controller.session.SessionConst;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.poolc.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
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
    public String create(@Valid @ModelAttribute("member") Member member,
        BindingResult bindingResult) {

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
    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, Model model,
        @RequestParam(value = "queryFilteredRole", defaultValue = "all") String queryFilteredRole) {

        log.debug("filteredRole = " + queryFilteredRole);
        Page<Member> members = filterDistributor(queryFilteredRole, pageable);
        //지정된 등급외의 잘못된 필터링용 input이 들어왔는지 validation (SRP인가...?)
        // 홈 화면으로 다시 리다이렉션 -> 혹시나 필터에서 걸리지 않은 비로그인 사용자 방지
        if (members.isEmpty()) {
            log.debug("잘못된 queryFilteredRole 쿼리스트링 값입니다.");
            return "redirect:/";
        }
        model.addAttribute("role_filter", queryFilteredRole);
        model.addAttribute("members", members);

        return "members/memberList";
    }

    // 회원들 필터링된 리스트 나열
    @PostMapping("/list")
    public String listAfterFilter(@PageableDefault Pageable pageable, Model model,
        @RequestParam("role_filter") String role_filter) {
        return "redirect:/members/list?queryFilteredRole=" + role_filter;
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
    public String update(
        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
        @Valid @ModelAttribute("member") Member member, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "members/updateMemberForm";
        }
        memberService.update(loginMember.getId(), member);

        return "redirect:/loginedMembers/logout";
    }

    private Page<Member> filterDistributor(String inputRoleFilter, Pageable pageable) {
        switch (inputRoleFilter) {
            case "all":
                return memberService.findAllPageById(pageable);
            case "admin":
                return memberService.findMemberPageByRole(MEMBER_ROLE.ROLE_ADMIN, pageable);
            case "gold":
                return memberService.findMemberPageByRole(MEMBER_ROLE.ROLE_GOLD, pageable);
            case "silver":
                return memberService.findMemberPageByRole(MEMBER_ROLE.ROLE_SILVER, pageable);
            case "bronze":
                return memberService.findMemberPageByRole(MEMBER_ROLE.ROLE_BRONZE, pageable);
            default:
                //올바르지 않은 role 감지시 Validation 필요.  -> all 변환.
                log.debug("올바르지 않은 role 입력입니다.");
                return Page.empty();
        }
    }

}
