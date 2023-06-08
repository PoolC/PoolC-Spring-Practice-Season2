package org.poolc.repository;

import java.util.Optional;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
//annotation 체크

public interface JpaMemberRepository extends JpaRepository<Member, Long> {
    //JPA를 사용해서 DB데이터를 조회하는 Repository 클래스, repository.findAll(pageable) 사용하기 위함.
    //JpaRepository는 @NoRepositoryBean 있음 -> 해당 interface 상속받은 interface가 bean등록 방지!(이것도 해당.)

    Optional<Member> findByUserId(String userId);

    //추후에 다른 table과 join시 count Query를 분리하여 별도의 join 없이 제공하기 위함.
    @Query(countQuery = "select count(m) from Member m")
    Page<Member> findByRole(MEMBER_ROLE m, Pageable pageable);

}