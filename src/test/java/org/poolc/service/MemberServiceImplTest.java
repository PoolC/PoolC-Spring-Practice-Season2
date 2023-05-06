package org.poolc.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.poolc.domain.Member;
import org.poolc.repository.MemoryMemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceImplTest {

    MemoryMemberRepository repository;
    MemberService memberService;
    PasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();;


    //매 동작 전전
    @BeforeEach
    public void beforeEach(){
        repository = new MemoryMemberRepository();
        memberService = new MemberServiceImpl(repository,bCryptPasswordEncoder);
    }


    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    void SuccessJoin() {
        Member member =new Member("star123","홍길동","good1234!","123@123","010-1212-1212",
                "컴퓨터과학","2012121212");

        memberService.join(member);

        Member result = repository.findByUserId("star123").get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    void DuplicateJoin() {
        Member member1 =new Member("star12355","홍길동","good1234!","123@123","010-1212-1212",
                "컴퓨터과학","2012121212");
        Member member2 =new Member("star12355","홍길동","good1234!","123@123","010-1212-1212",
                "컴퓨터과학","2012121212");

        memberService.join(member1);
        Assertions.assertThatThrownBy(()->memberService.join(member2))
                .isInstanceOf(java.lang.IllegalStateException.class);
    }

    @Test
    void findMembers() {
        Member member1 =new Member("star12355","홍길동","good1234!","123@123","010-1212-1212",
                "컴퓨터과학","2012121212");
        Member member2 =new Member("star234555","성종빈","good2345!","123@1235","010-1212-12123",
                "컴퓨터과학","201814777");

        memberService.join(member1);
        memberService.join(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        Member member1 =new Member("star12355","홍길동","good1234!","123@123","010-1212-1212",
                "컴퓨터과학","2012121212");
        Member member2 =new Member("star234555","성종빈","good2345!","123@1235","010-1212-12123",
                "컴퓨터과학","201814777");

        memberService.join(member1);
        memberService.join(member2);

        Member result = memberService.findOne(member2.getId()).get();
        assertThat(result).isEqualTo(member2);
    }

}
