package com.emailschedule.service;

import com.emailschedule.dto.request.RegisterRequestDto;
import com.emailschedule.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    public String register(RegisterRequestDto dto) {
       return null;
    }
}
