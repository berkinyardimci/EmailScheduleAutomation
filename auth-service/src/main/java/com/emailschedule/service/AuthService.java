package com.emailschedule.service;

import com.emailschedule.converter.ConvertLoginResponse;
import com.emailschedule.converter.ConvertRequestToAuth;
import com.emailschedule.dto.request.LoginRequestDto;
import com.emailschedule.dto.request.RegisterRequestDto;
import com.emailschedule.dto.response.LoginResponse;
import com.emailschedule.dto.response.RegisterResponse;
import com.emailschedule.entity.Auth;
import com.emailschedule.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final JwtTokenManager jwtTokenManager;

    public RegisterResponse register(RegisterRequestDto request) {

        if (!authRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email Kullaılıyor");
        }
        authRepository.save(ConvertRequestToAuth.toAuth(request));
        return RegisterResponse.builder()
                .isSuccess(true)
                .build();
    }

    public LoginResponse login(LoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

        return auth.map(validAuth -> {
                    String token = jwtTokenManager.createToken(validAuth)
                            .orElseThrow(() -> new RuntimeException("Token could not be generated"));
                    return ConvertLoginResponse.loginResponse(validAuth, token);
                })
                .orElseThrow(() -> new RuntimeException("User Bulunamadı"));
    }
}
