package com.emailschedule.service;

import com.emailschedule.constant.ErrorMessages;
import com.emailschedule.converter.ConvertLoginResponse;
import com.emailschedule.converter.ConvertRequestToAuth;
import com.emailschedule.dto.request.LoginRequestDto;
import com.emailschedule.dto.request.RegisterRequestDto;
import com.emailschedule.dto.response.LoginResponse;
import com.emailschedule.dto.response.RegisterResponse;
import com.emailschedule.entity.Auth;
import com.emailschedule.exception.EmailAlreadyExistException;
import com.emailschedule.exception.InvalidPasswordException;
import com.emailschedule.exception.PasswordNotMatchesException;
import com.emailschedule.exception.UserNotFoundException;
import com.emailschedule.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final JwtTokenManager jwtTokenManager;
    private final BCryptPasswordEncoder passwordEncoder;


    public RegisterResponse register(RegisterRequestDto request) {

        if (authRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistException(ErrorMessages.EMAIL_ALREADY_EXIST.getMessage());
        }
        if (!Objects.equals(request.getPassword(), request.getRePassword())) {
            throw new PasswordNotMatchesException(ErrorMessages.PASSWORD_NOT_MATCHES.getMessage());
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        authRepository.save(ConvertRequestToAuth.toAuth(request));
        return RegisterResponse.builder()
                .isSuccess(true)
                .build();
    }

    public LoginResponse login(LoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findByEmail(dto.getEmail());
        if (!passwordEncoder.matches(dto.getPassword(), auth.get().getPassword())) {
            throw new InvalidPasswordException(ErrorMessages.INVALID_PASSWORD.getMessage());
        }

        return auth.map(validAuth -> {
                    String token = jwtTokenManager.createToken(validAuth)
                            .orElseThrow(() -> new RuntimeException("Token could not be generated"));
                    return ConvertLoginResponse.loginResponse(token);
                })
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage()));
    }
}
