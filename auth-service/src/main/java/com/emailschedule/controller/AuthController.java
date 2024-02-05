package com.emailschedule.controller;

import com.emailschedule.dto.request.LoginRequestDto;
import com.emailschedule.dto.request.RegisterRequestDto;
import com.emailschedule.dto.response.LoginResponse;
import com.emailschedule.dto.response.RegisterResponse;
import com.emailschedule.model.TokenModel;
import com.emailschedule.service.AuthService;
import com.emailschedule.service.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequestDto request) {
        RegisterResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto dto) {
        LoginResponse responseDto = authService.login(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getEmailFromToken")
    public Optional<String> getEmailFromToken(@RequestParam("token") String token) {
        System.out.println(token);
        return jwtTokenManager.getEmailFromToken(token);
    }


    @GetMapping("/notsecure")
    public ResponseEntity<String> getNotSecure() {
        return ResponseEntity.ok("Güvenlik yokkk");
    }

    @GetMapping("/secure")
    public ResponseEntity<String> getSecure() {
        return ResponseEntity.ok("Güvenlik Varrr");
    }
}
