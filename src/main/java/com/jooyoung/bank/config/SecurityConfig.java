package com.jooyoung.bank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jooyoung.bank.domain.user.UserEnum;
import com.jooyoung.bank.dto.ResponseDto;
import com.jooyoung.bank.util.CustomResponseUtil;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean //Ioc 컨테이너에 BCryptPasswordEncoder 객체가 등록됨
    //해당 Bean은 @Configuration을 붙인 클래스의 빈만 등록됨
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    //JWT 필터 적용

    //JWT 서버를 만들 예정 > Session 사용 X
    //Security의 Default 값
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.debug("디버그 : filterChain 빈 등록됨");
        http.headers().frameOptions().disable(); //iframe 허용안함
        http.csrf().disable(); //enable 이면 post맨 작동 안함
        http.cors().configurationSource(configurationSource());

        //jSeesionId를 서버쪽에서 관리 안하겠다는 뜻
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //react, app
        http.formLogin().disable(); // 폼 로그인 안쓰겠다는 뜻
        //브라우저가 팝업창을 이용해서 사용자 인증을 진행 못하도록 설정
        http.httpBasic().disable();

        //Exception
        //인증이나 권환에 관련된 내용을 시큐리티가 가져가지 못하고 우리가 가로챌 수 있도록 설정
        http.exceptionHandling().authenticationEntryPoint((request, response, authException)->{

            CustomResponseUtil.unAuthentication(response, "로그인을 진행해주세요.");


        });

        http.authorizeHttpRequests()
                .antMatchers("/api/s/**").authenticated() //주소에 s가 들어오면 인증이 필요 >> 401
                .antMatchers("/api/admin/**").hasRole(""+UserEnum.ADMIN) // 주소에 admin이 있는 경우에는 UserEnum.ADMIN인 사용자만 허용 >> 40
            .anyRequest().permitAll();//나머지 요청은 허용

        return http.build();

    }

    public CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration= new CorsConfiguration();
        configuration.addAllowedHeader("*"); //모든 헤더 다 받겠다
        configuration.addAllowedMethod("*"); //모든 메서드 다 받겠다 > GET POST PUT DELETE
        configuration.addAllowedOriginPattern("*"); // 모든 주소 허용 ( Frontend 쪽 IP만 허용 )
        configuration.setAllowCredentials(true); // 클라이언트 쪽에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 주소에서든 위에 설정한 규칙(configuration)을 적용시키겠다.

        return source;
    }
}
















