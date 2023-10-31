package com.jooyoung.bank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jooyoung.bank.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

public class CustomResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);
    //getClass가 안되는 이유 ?

    public static void unAuthentication(HttpServletResponse response, String msg){
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(-1, "인증없음", null);
            String responseBody = om.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(401);
            response.getWriter().println(responseBody); // 예쁘게 메세지를 포장하는 공통적인 응답 DTO
        }catch(Exception ex){
            log.error("서버 파싱 에러");
        }
    }

}
