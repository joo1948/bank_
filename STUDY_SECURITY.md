# Study

## Spring Security
- Spring 기반의 애플리케이션의 보안(인증과 권한, 인가 등)을 담당하는 스프링 하위 프레임워크
- 인증을 거친 후에 인가의 작업을 거침
- Authentication(인증) > Authorization(인가)
    - 인증 : 해당 사용자가 맞는지  ?
    - 인가 : 해당 사용자가 해당 권한이 있는지 ?
- pring Security에서는 이러한 인증과 인가를 위해 Principal을 아이디로, Credential을 비밀번호로 사용하는  <strong>Credential 기반의 인증 방식</strong>을 사용한다.
  - Principal(접근 주체): 보호받는 Resource에 접근하는 대상
  - Credential(비밀번호): Resource에 접근하는 대상의 비밀번호

### SecurityConfig 파일의 파라미터
1. SecurityFilterChain
   - http.headers().frameOptions.disable()
     - iframe 허용 x
   - http.csrf().disable()
     - enable이면 post맨 작동 안함
   - http.cors().configurationSource(configurationSource())
     - configurationSource() 함수에서 반환하는 값들을 적용하겠다.
   - http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
     - jSessionID를 서버쪽에서 관리하지 않겠다.
     
        <details>
          <summary>jSessionID란 ? </summary>
            ```java  
       
            // 세션을 가져오거나 생성
              HttpSession session = request.getSession();
            // 세션에 데이터 저장
            session.setAttribute("username", "user123");
            
            // JSESSIONID 쿠키 설정
            // 주의: 일반적으로 이 부분을 직접 구현할 필요는 없습니다. Servlet 컨테이너가 자동으로 처리합니다.
            
            // 응답에 JSESSIONID 쿠키 추가
            String sessionId = session.getId();
            response.addCookie(new Cookie("JSESSIONID", sessionId));
        
           ```
        </details>


       
       
       
   - http.formLogin().disable()
       - form 로그인 사용하지 않겠다
   - http.httpBasic.disable()
     - 브라우저가 팝업창을 이용해서 사용자 인증을 진행 못하도록 설정
   - http.authorizeHttpRequest()
     .antMatchers("/api/s/**").authenticated()
     .antMatchers("/api/admin/**").hasRole(""+UserEnum.ADMIN) 
     .anyRequest().permitAll();
     - 주소에 /api/s/치고 들어오는 것들은 모두 인증이 필요
     - 주소에 /api/admin/치고 들어오는 것들은 UserEnum.ADMIN 타입이여야 함.
     - 나머지는 허용


2. CorsConfigurationSource
   - configuration.addAllowedHeader("*")
     - 모든 헤더요청 받겠다.
   - addAllowedMethod("*")
     - 모든 http Method 요청 받겠다.
   - addAllowedOriginPattern("*")
     - 모든 주소 허용하겠다. ( Front 쪽에서 들어오는 주소 )
   - setAllowCredentials(true)
     - 클라이언트 쪽에서 들어오는 쿠키 요청 허용
   - UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();<br>
     source.registerCorsConfiguration("/**", configuration)
     - 모든 주소에서든 위에 configuration으로 설정한 규칙을 적용할 것
---

