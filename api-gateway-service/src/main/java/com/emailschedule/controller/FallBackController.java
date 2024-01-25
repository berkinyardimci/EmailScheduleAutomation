package com.emailschedule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @GetMapping("/email")
    public ResponseEntity<String> authServiceFallBack(){
        return ResponseEntity.ok("Email Schedule Service Suanda Hizmet veremektedir.");
    }

}
