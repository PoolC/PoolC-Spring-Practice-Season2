package org.poolc.service;

import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.poolc.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    //member Repository를 외부에서 넣어주도록 설계.
    public MemberService(MemberRepository memberRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public Long join(Member member) {
        validateDuplicateMember(member); //중복회원 검증 후 비밀번호 암호화해서 저장
        member.setPassWord(bCryptPasswordEncoder.encode(member.getPassWord()));
        memberRepository.save(member);

        return member.getId();
    }


    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }


    public Optional<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    private void validateDuplicateMember(Member member) {
        //ifPresent-> 해당 userID를 가지고 있는 user가 있으면 예외 발생
        memberRepository.findByUserId(member.getUserId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    @Transactional //DB에서 자동으로 수정된 정보 반영
    public Long update(Long id, Member afterMember) {
        validateDuplicateMember(afterMember);
        afterMember.setPassWord(bCryptPasswordEncoder.encode(afterMember.getPassWord()));
        Member beforeMember = memberRepository.findById(id).get();
        beforeMember.setEmail(afterMember.getEmail());
        beforeMember.setUserId(afterMember.getUserId());
        beforeMember.setName(afterMember.getName());
        beforeMember.setPassWord(afterMember.getPassWord());
        beforeMember.setPhoneNum(afterMember.getPhoneNum());
        beforeMember.setDepartment(afterMember.getDepartment());
        beforeMember.setStudentId(afterMember.getStudentId());
        return beforeMember.getId();
    }

    @Transactional //ROle 변경
    public Long RoleModify(Long id, MEMBER_ROLE member_role) {
        Member member = memberRepository.findById(id).get();
        member.setRole(member_role);
        return member.getId();
    }
}
