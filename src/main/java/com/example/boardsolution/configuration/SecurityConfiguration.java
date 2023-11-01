package com.example.boardsolution.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
//Security filterchain을 구성하기 위한 어노테이션
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //비밀번호 암호화를 위한 PasswordEncoder
    //복호화가 불가능. match라는 메소드를 이용해서 사용자의 입력값과 DB의 저장값을 비교
    // => true나 false 리턴, match(암호화되지 않은 값, 암호화된 값)
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //필터 체인 구현(HttpSecurity 객체 사용)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(httpSecurityCorsConfigurer -> {})
                //csrf 공격에 대한 옵션 꺼두기
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(httpSecurityHttpBasicConfigurer -> {
                    httpSecurityHttpBasicConfigurer.disable();
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    );
                })
                //요청 주소에 대한 권한 설정
              .authorizeHttpRequests((authorizeRequests) -> {
                    //'/'요청은 모든 사용자가 이용가능
                    authorizeRequests.antMatchers("/").permitAll();
                    //css, js, images, upload 같은 정적 리소스들도 권한처리 필수
                    authorizeRequests.antMatchers("/upload/**").permitAll();
                    //게시판 기능은 권한을 가지고 있는 사용자만 사용가능
                    authorizeRequests.antMatchers("/board/**").hasAnyRole("ADMIN", "USER");
                    //관리자 페이지는 관리자만 사용가능
                    authorizeRequests.antMatchers("/admin/**").hasRole("ADMIN");
                    //회원가입, 로그인, 아이디중복체크 등 요청은 모든 사용자가 사용가능
                    authorizeRequests.antMatchers("/user/join").permitAll();
                    authorizeRequests.antMatchers("/igiveyougps").permitAll();
                    authorizeRequests.antMatchers("/hihi").permitAll();
                    authorizeRequests.antMatchers("/receivephoto").permitAll();
                    authorizeRequests.antMatchers("/trytogetphotofromserver").permitAll();
//                    authorizeRequests.requestMatchers("/user/**").permitAll();
                    authorizeRequests.antMatchers("/user/id-check").permitAll();
                    authorizeRequests.antMatchers("/user/getuser").permitAll();
                    authorizeRequests.antMatchers("/user/getstudent").permitAll();
                    authorizeRequests.antMatchers("/user/login").permitAll();
                    authorizeRequests.antMatchers("/user/update").permitAll();
                    authorizeRequests.antMatchers("/user/updatepassword").permitAll();
                    authorizeRequests.antMatchers("/user/user-list").permitAll();
                    authorizeRequests.antMatchers("/api/**").permitAll();
                    authorizeRequests.antMatchers("/user/**").permitAll();
                    authorizeRequests.antMatchers("/notice/**").permitAll();
                    authorizeRequests.antMatchers("/course/**").permitAll();
                    authorizeRequests.antMatchers("/sms/**").permitAll();
                    authorizeRequests.antMatchers("/myba/**").permitAll();
                    authorizeRequests.antMatchers("/user/deleteselectusers").permitAll();
                    //이외의 요청은 인증된 사용자만 사용자만 사용가능
                    authorizeRequests.anyRequest().authenticated();
                })
//                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
                .build();
    }









}
