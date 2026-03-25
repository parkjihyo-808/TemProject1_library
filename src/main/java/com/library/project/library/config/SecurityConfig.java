package com.library.project.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 설정을 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1. CSRF 보안 비활성화 (개발 단계 및 API 테스트 편의를 위해)
        http.csrf(csrf -> csrf.disable());

        // 2. 경로별 권한 설정
        http.authorizeHttpRequests(auth -> auth
                // 누구나 접근 가능한 경로 (메인, 로그인, 회원가입, 정적 리소스)
                .requestMatchers("/", "/member/login", "/member/join", "/css/**", "/js/**", "/img/**").permitAll()
                // 로그인한 사용자만 접근 가능한 경로 (마이페이지, 행사/강좌 신청 내역)
                .requestMatchers("/member/mypage", "/member/apply-list").authenticated()
                // 그 외 모든 요청은 허용 (프로젝트 진행을 위해 일단 개방)
                .anyRequest().permitAll()
        );

        // 3. 로그인 설정 (누나의 login.html 속성값에 맞춤)
        http.formLogin(form -> form
                .loginPage("/member/login")             // 커스텀 로그인 페이지 경로
                .loginProcessingUrl("/member/login")    // HTML Form의 action 경로
                .usernameParameter("mid")               // input name="mid"
                .passwordParameter("mpw")               // input name="mpw"
                .defaultSuccessUrl("/", true)           // 로그인 성공 시 메인(/)으로 강제 이동
                .failureUrl("/member/login?error=true") // 로그인 실패 시 에러 파라미터와 함께 이동
                .permitAll()
        );

        // 4. 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/member/logout")            // 로그아웃 처리 주소
                .logoutSuccessUrl("/")                  // 로그아웃 성공 시 메인으로
                .invalidateHttpSession(true)            // 세션 삭제
                .deleteCookies("JSESSIONID")            // 쿠키 삭제
        );

        return http.build();
    }

    // 비밀번호 암호화 도구 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}