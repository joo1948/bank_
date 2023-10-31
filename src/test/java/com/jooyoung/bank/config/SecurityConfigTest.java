package com.jooyoung.bank.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.plugins.MockMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc // Mock(가짜) 환경에 MockMvc가 등록됨
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SecurityConfigTest {

    @Autowired    // 가짜 환경에 등록된 MockMvc를 DI함
    private MockMvc mvc;

    //서버는 일관성 있게 에러가 리턴되어야 한다
    // 내가 모르는 에러가 프론트에 날라가지 않게, 내가 직접 다 제어하자.
    @Test
    public void autehntication_test() throws Exception {
        //given
        final String url = "/api/s/hello";
        // 해당 api 주소가 /api/s/** 이기에 로그인 안했을 때 401이 떨어진다.

        //when
        ResultActions resultActions = mvc.perform(get(url));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString(); //응답 컨텐트 내용을 반환해라.
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus(); //응답 상태코드 반환해라
        System.out.println("테스트 : "+responseBody);
        System.out.println("테스트 코드: "+httpStatusCode);
        //then
        assertThat(httpStatusCode).isEqualTo(401);
    }

    @Test
    public void authorization_test() throws Exception {
        //given
        final String url = "/api/admin/hello";
        // 해당 api 주소가 /api/admin/** 이기에 로그인 안했을 때 403이 떨어진다.

        //when
        ResultActions resultActions = mvc.perform(get(url));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString(); //응답 컨텐트 내용을 반환해라.
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus(); //응답 상태코드 반환해라
        System.out.println("테스트 : "+responseBody);
        System.out.println("테스트 코드: "+httpStatusCode);

        //then
        assertThat(httpStatusCode).isEqualTo(403);
    }
}