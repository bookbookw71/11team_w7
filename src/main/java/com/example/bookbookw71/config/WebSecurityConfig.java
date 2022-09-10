package com.example.bookbookw71.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //암호화 알고리즘
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 회원 관리 처리 API (POST /member/**) 에 대해 CSRF 무시
        http.csrf()
                .ignoringAntMatchers("/member/**");

        http.authorizeRequests()
                //image,css 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                .antMatchers("/css/**").permitAll()
                // 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/memberr/**").permitAll()
                //그 외 요청에는 인증
                .anyRequest().authenticated()
                .and()
                //로그인 기능
                .formLogin()
                .loginPage("/api/member/login")
                .defaultSuccessUrl("/")
                .failureUrl("/member/login?error")
                .permitAll()
                .and()
                //로그아웃 기능
                .logout()
                .permitAll();
    }
}