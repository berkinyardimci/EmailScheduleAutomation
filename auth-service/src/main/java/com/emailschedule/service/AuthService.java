package com.emailschedule.service;

import com.emailschedule.converter.ConvertRequestToAuth;
import com.emailschedule.dto.request.RegisterRequestDto;
import com.emailschedule.dto.response.RegisterResponse;
import com.emailschedule.entity.Auth;
import com.emailschedule.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public RegisterResponse register(RegisterRequestDto request) {

        if (!authRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email Kullaılıyor");
        }
        authRepository.save(ConvertRequestToAuth.toAuth(request));
        return RegisterResponse.builder()
                .isSuccess(true)
                .build();
    }


}
