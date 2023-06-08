package org.poolc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.poolc.repository.JpaMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    JpaMemberRepository repository;
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    EntityManager em;

    //test도 중복 부분이 많다 -> 나중에 리팩터링으로 중복되는 부분 따로 메소드 만들어서 생략할 것!

    @Test
    void SuccessJoin() {
        Member member = new Member("star123", "홍길동", "good1234!", "123@123", "010-1212-1212",
            "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);

        memberService.join(member);

        Member result = repository.findByUserId("star123").get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    void DuplicateJoin() {
        Member member1 = new Member("star12355", "홍길동", "good1234!", "123@123", "010-1212-1212",
            "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);
        Member member2 = new Member("star12355", "홍길동", "good1234!", "123@123", "010-1212-1212",
            "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);

        memberService.join(member1);
        Assertions.assertThatThrownBy(() -> memberService.join(member2))
            .isInstanceOf(java.lang.IllegalStateException.class);
    }

    @Test
    void findAll() {
        Member member1 = new Member("star12355", "홍길동", "good1234!", "123@123", "010-1212-1212",
            "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);
        Member member2 = new Member("star234555", "성종빈", "good2345!", "123@1235", "010-1212-12123",
            "컴퓨터과학", "201814777", MEMBER_ROLE.ROLE_SILVER);

        final int currentNumOFMember = repository.findAll().size();
        memberService.join(member1);
        memberService.join(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(currentNumOFMember + 2);
    }

    @Test
    void findMembers() {
        Member member1 = new Member("star12355", "홍길동", "good1234!", "123@123", "010-1212-1212",
            "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_ADMIN);
        Member member2 = new Member("star234555", "성종빈", "good2345!", "123@1235", "010-1212-12123",
            "컴퓨터과학", "201814777", MEMBER_ROLE.ROLE_SILVER);

        memberService.join(member1);
        memberService.join(member2);
        Optional<List<Member>> listMember1 = memberService.findMembers(member1.getRole());
        Optional<List<Member>> listMember2 = memberService.findMembers(member2.getRole());

        assertThat(listMember1.isEmpty()).isEqualTo(false);
        assertThat(listMember2.isEmpty()).isEqualTo(true);
    }

    @Test
    void findOne() {
        Member member1 = new Member("star12355", "홍길동", "good1234!", "123@123", "010-1212-1212",
            "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);
        Member member2 = new Member("star234555", "성종빈", "good2345!", "123@1235", "010-1212-12123",
            "컴퓨터과학", "201814777", MEMBER_ROLE.ROLE_SILVER);

        memberService.join(member1);
        memberService.join(member2);

        Member result = memberService.findOne(member2.getId()).get();
        assertThat(result).isEqualTo(member2);
    }

    @Test
    void findMemberPageByRole() {
        Member member1 = new Member("star12355", "홍길동", "good1234!", "123@123", "010-1212-1212",
            "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);
        Member member2 = new Member("star234555", "성종빈", "good2345!", "123@1235", "010-1212-12123",
            "컴퓨터과학", "201814777", MEMBER_ROLE.ROLE_SILVER);

        memberService.join(member1);
        memberService.join(member2);
        Pageable pageable = PageRequest.of(0, 2);
        Pageable pageable2 = PageRequest.of(1, 2);
        Page<Member> result = memberService.findMemberPageByRole(MEMBER_ROLE.ROLE_SILVER, pageable);
        assertThat(result.getTotalPages()).isEqualTo(1);
        Page<Member> result2 = memberService.findMemberPageByRole(MEMBER_ROLE.ROLE_SILVER,
            pageable2);
        assertThat(result2.getTotalPages()).isEqualTo(1);
    }

    @Test
    void findAllPageById() {
        Member member1 = new Member("star12355", "홍길동", "good1234!", "123@123", "010-1212-1212",
            "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);
        Member member2 = new Member("star234555", "성종빈", "good2345!", "123@1235", "010-1212-12123",
            "컴퓨터과학", "201814777", MEMBER_ROLE.ROLE_SILVER);

        memberService.join(member1);
        memberService.join(member2);
        Pageable pageable = PageRequest.of(0, 2);
        Pageable pageable2 = PageRequest.of(1, 2);
        Page<Member> result = memberService.findAllPageById(pageable);
        assertThat(result.isEmpty()).isEqualTo(false);
        Page<Member> result2 = memberService.findAllPageById(pageable2);
        assertThat(result2.isEmpty()).isEqualTo(false);
    }


    @Test
    void findAllPageByIdDesc() {
        Member member1 = new Member("star12355", "홍길동", "good1234!", "123@123", "010-1212-1212",
            "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);
        Member member2 = new Member("star234555", "성종빈", "good2345!", "123@1235", "010-1212-12123",
            "컴퓨터과학", "201814777", MEMBER_ROLE.ROLE_SILVER);

        memberService.join(member1);
        memberService.join(member2);
        Pageable pageable = PageRequest.of(0, 2);
        Pageable pageable2 = PageRequest.of(1, 2);
        Page<Member> result = memberService.findAllPageByIdDesc(pageable);
        assertThat(result.isEmpty()).isEqualTo(false);
        Page<Member> result2 = memberService.findAllPageByIdDesc(pageable2);
        assertThat(result2.isEmpty()).isEqualTo(false);
    }


}
