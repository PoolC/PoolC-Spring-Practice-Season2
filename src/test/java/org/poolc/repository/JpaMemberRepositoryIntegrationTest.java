package org.poolc.repository;

import org.junit.jupiter.api.Test;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
public class JpaMemberRepositoryIntegrationTest {

    @Autowired
    MemberRepository repository;

    @Test
    public void saveAndFindById() {
        //given
        Member member = new Member("star123", "홍길동", "good1234!", "123@123", "010-1212-1212",
                "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }


    @Test
    public void findByUserId() {

        Member member1 = new Member("star1234", "spring1", "good1234!", "123@123", "010-1212-1212",
                "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);
        repository.save(member1);

        Member member2 = new Member("star1234", "spring2", "good2345!", "123@1235", "010-1212-12123",
                "컴퓨터과학", "20121212123", MEMBER_ROLE.ROLE_SILVER);
        repository.save(member2);

        Member result = repository.findByUserId("star1234").get();
        assertThat(result).isEqualTo(member1);
    }


    @Test
    public void findAll() {
        Member member1 = new Member("star1234", "spring1", "good1234!", "123@123", "010-1212-1212",
                "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);
        repository.save(member1);

        Member member2 = new Member("star1234", "spring2", "good2345!", "123@1235", "010-1212-12123",
                "컴퓨터과학", "20121212123", MEMBER_ROLE.ROLE_SILVER);
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(4);
    }
}
