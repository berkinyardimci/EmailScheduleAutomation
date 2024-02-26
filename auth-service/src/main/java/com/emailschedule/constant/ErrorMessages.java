package com.emailschedule.constant;

import com.emailschedule.exception.PasswordNotMatchesException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {

    EMAIL_ALREADY_EXIST("Email Kullanılıyor."),
    USER_NOT_FOUND("Kullanıcı Bulunamadı."),
    PASSWORD_NOT_MATCHES("Şifreler Uyuşmuyor"),
    INVALID_PASSWORD("Hatalı Şifre");

    private final String message;
}
