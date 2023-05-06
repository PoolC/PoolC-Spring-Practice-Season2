package org.poolc.config;



import org.poolc.repository.JpaMemberRepository;
import org.poolc.repository.MemberRepository;
import org.poolc.repository.MemoryMemberRepository;
import org.poolc.service.MemberService;
import org.poolc.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Configuration
public class SpringConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MemberService memberService(){

        return new MemberServiceImpl(memberRepository(),bCryptPasswordEncoder() );
    }

    @Bean
    public MemberRepository memberRepository(){

        //return new MemoryMemberRepository();
        return new JpaMemberRepository(em);
    }
}
