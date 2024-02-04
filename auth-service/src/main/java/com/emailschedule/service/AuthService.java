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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final JwtTokenManager jwtTokenManager;
    private final BCryptPasswordEncoder passwordEncoder;


    public RegisterResponse register(RegisterRequestDto request) {

        if (authRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email Kullaılıyor");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        authRepository.save(ConvertRequestToAuth.toAuth(request));
        return RegisterResponse.builder()
                .isSuccess(true)
                .build();
    }

    public LoginResponse login(LoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findByEmail(dto.getEmail());
        if(!passwordEncoder.matches(dto.getPassword(), auth.get().getPassword())){
            throw new RuntimeException("Şifreler Yanlış");
        }

        return auth.map(validAuth -> {
                    String token = jwtTokenManager.createToken(validAuth)
                            .orElseThrow(() -> new RuntimeException("Token could not be generated"));
                    return ConvertLoginResponse.loginResponse(token);
                })
                .orElseThrow(() -> new RuntimeException("User Bulunamadı"));
    }
}
