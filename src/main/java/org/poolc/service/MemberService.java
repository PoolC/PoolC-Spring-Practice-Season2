package org.poolc.service;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.poolc.domain.MEMBER_ROLE;
import org.poolc.domain.Member;
import org.poolc.repository.JpaMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MemberService {

    private final JpaMemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;


    //member Repository를 외부에서 넣어주도록 설계.
    public MemberService(JpaMemberRepository memberRepository,
        PasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public Long join(Member member) {
        validateDuplicateMember(member); //중복회원 검증 후 비밀번호 암호화해서 저장
        member.setPassWord(bCryptPasswordEncoder.encode(member.getPassWord()));
        memberRepository.save(member);

        return member.getId();
    }


    @NotNull
    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }


    //admin용 전체 멤버 불러오기 method -> data가 없거나, admin이 아닐시 empty로 controller단에서 Validation 진행
    // 2차 Validation (validation하기 최적의 위치? controller vs service)
    @NotNull
    public Optional<List<Member>> findMembers(MEMBER_ROLE member_role) {
        return member_role != MEMBER_ROLE.ROLE_ADMIN ? Optional.empty() :
            Optional.ofNullable(memberRepository.findAll());
    }

    @NotNull
    public Optional<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);

    }

    @NotNull
    public Page<Member> findMemberPageByRole(MEMBER_ROLE m, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0
            : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);

        return memberRepository.findByRole(m, pageable);
    }

    @NotNull
    public Page<Member> findAllPageByIdDesc(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0
            : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by("id").descending());

        return memberRepository.findAll(pageable);
    }

    @NotNull
    public Page<Member> findAllPageById(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0
            : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by("id"));

        return memberRepository.findAll(pageable);
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
