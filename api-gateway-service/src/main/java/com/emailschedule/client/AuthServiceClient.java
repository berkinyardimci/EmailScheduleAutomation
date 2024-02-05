package com.emailschedule.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



import java.util.Optional;

@FeignClient(url = "http://localhost:9092/auth", name = "gateway-auth")
public interface AuthServiceClient {

    @GetMapping("/getEmailFromToken")
    Optional<String> getEmailFromToken(@RequestParam("token") String token);
}
