package com.emailschedule.converter;

import com.emailschedule.dto.response.LoginResponse;
import com.emailschedule.entity.Auth;

public class ConvertLoginResponse {
    public static LoginResponse loginResponse(String token) {
        return LoginResponse.builder()
                .token(token)
                .isSuccess(true)
                //.roles(auth.getRoles())
                .build();
    }
}
