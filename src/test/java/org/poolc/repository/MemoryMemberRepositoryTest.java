package org.poolc.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();


    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void saveAndFindById() {
        //given
        Member member = new Member("star", "spring1", "good1234!", "123@123", "010-1212-1212",
                "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByUserId() {
        Member member1 = new Member("star1234", "sprin", "good1234!", "123@123", "010-1212-1212",
                "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);
        Member member2 = new Member("sta2345", "spring2", "good2345!", "123@1235", "010-1212-12123",
                "컴퓨터과학", "20121212123", MEMBER_ROLE.ROLE_SILVER);
        repository.save(member1);
        repository.save(member2);

        Member result = repository.findByUserId("star1234").get();
        assertThat(result).isEqualTo(member1);
    }


    @Test
    public void findAll() {
        Member member1 = new Member("star2345", "spring1", "good1234!", "123@123", "010-1212-1212",
                "컴퓨터과학", "2012121212", MEMBER_ROLE.ROLE_SILVER);
        repository.save(member1);

        Member member2 = new Member("star1234", "spring2", "good2345!", "123@1235", "010-1212-12123",
                "컴퓨터과학", "20121212123", MEMBER_ROLE.ROLE_SILVER);
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
