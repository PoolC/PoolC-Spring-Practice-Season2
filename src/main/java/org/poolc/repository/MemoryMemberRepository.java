package org.poolc.repository;

import org.poolc.domain.Member;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


public class MemoryMemberRepository implements MemberRepository {

    //저장공간 ->key:회원id/value:Member static으로 공유변수일때는 동시성 문제때문에 concurrnetHashmap써야함
    private static Map<Long, Member> store = new HashMap<>();
    //키값을 생성해주는얘, 애도 동시성 문제때문에 atom long등을 해주어야함
    private static AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Member save(Member member) {
        member.setId(sequence.incrementAndGet());
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return store.values().stream().filter(member -> member.getUserId().equals(userId))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }


    public void clearStore() {
        store.clear();
    }
}
