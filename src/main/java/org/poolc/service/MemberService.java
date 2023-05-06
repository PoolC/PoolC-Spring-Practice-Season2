package org.poolc.service;

import org.poolc.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Long join(Member member);
    List<Member> findMembers();
    Optional<Member> findOne(Long memberId);
    Optional<Member> findByUserId(String userId);
    Long update(Long id, Member afterMember);

}
