package com.emailschedule.repository;

import com.emailschedule.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth,Long> {
    boolean existsByEmail(String email);

    Optional<Auth> findByEmailAndPassword(String email, String password);
}

