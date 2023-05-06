package org.poolc.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class MemberTest {

    @Test
    void constructorTest(){
        Member member =new Member();
        member.setUserId("123");
        member.setPassWord("123");
        member.setName("123");
        member.setDepartment("123");
        member.setEmail("123");
        member.setStudentId("123");
        member.setPhoneNum("123");
        Member member2 = new Member("123","123","123",
                "123","123","123","123");
        assertThat(member.getUserId()).isEqualTo(member2.getUserId());
    }

    @Test
    void getId() {
        Member member = new Member();
        member.setId(99999L);
        assertThat(99999L).isEqualTo(member.getId());
    }

    @Test
    void getUserId() {
        Member member = new Member();
        member.setUserId("123");
        assertThat("123").isEqualTo(member.getUserId());
    }

    @Test
    void getName() {
        Member member = new Member();
        member.setName("123");
        assertThat("123").isEqualTo(member.getName());
    }

    @Test
    void getPassWord() {
        Member member = new Member();
        member.setPassWord("123");
        assertThat("123").isEqualTo(member.getPassWord());
    }

    @Test
    void getEmail() {
        Member member = new Member();
        member.setEmail("123");
        assertThat("123").isEqualTo(member.getEmail());
    }

    @Test
    void getPhoneNum() {
        Member member = new Member();
        member.setPhoneNum("123");
        assertThat("123").isEqualTo(member.getPhoneNum());
    }

    @Test
    void getDepartment() {
        Member member = new Member();
        member.setDepartment("123");
        assertThat("123").isEqualTo(member.getDepartment());
    }

    @Test
    void getStudentId() {
        Member member = new Member();
        member.setStudentId("123");
        assertThat("123").isEqualTo(member.getStudentId());
    }



    @Test
    void testEquals() {
        Member member = new Member();
        Member member1 = member;
        assertThat(member1.equals(member)).isEqualTo(true);
    }


}