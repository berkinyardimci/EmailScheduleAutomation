package com.emailschedule.config;

import com.emailschedule.entity.Auth;
import com.emailschedule.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoggedInUserDetailService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Burası çalıştııı");
        Optional<Auth> credential = authRepository.findByEmail(email);
        return credential.map(LoggedInUser::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + email));
    }
}


