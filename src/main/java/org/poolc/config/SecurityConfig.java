package org.poolc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf().disable() // post 방식으로 값을 전송할 때 token을 사용해야하는 보안 설정을 해제
                .authorizeRequests()
                .antMatchers("/", "/members/**", "/login", "/loginedMembers/**", "/role", "/admin/**").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        return http.build();
    }
}
