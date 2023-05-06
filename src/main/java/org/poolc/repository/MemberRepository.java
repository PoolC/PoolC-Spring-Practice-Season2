package org.poolc.repository;

import org.poolc.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByUserId(String userId);
    List<Member> findAll();
}
