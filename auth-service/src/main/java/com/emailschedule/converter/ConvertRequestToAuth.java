package com.emailschedule.converter;

import com.emailschedule.dto.request.RegisterRequestDto;
import com.emailschedule.entity.Auth;


public class ConvertRequestToAuth {

    public static Auth toAuth(RegisterRequestDto dto) {
        return Auth.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
