package org.poolc.service;

import lombok.RequiredArgsConstructor;
import org.poolc.domain.Member;
import org.poolc.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @return null->로그인 실패
     */
    public Member login(String loginId, String password) {
        //로그인 시도한 아이디가 있는지 체크 ->없으면 null반환.
        Optional<Member> findMember = memberRepository.findByUserId(loginId);
        //암호화된 비밀번호화 Match 되었는지 체크
        return findMember.filter(m -> passwordEncoder.matches(password, m.getPassWord()))
                .orElse(null);

    }

}
