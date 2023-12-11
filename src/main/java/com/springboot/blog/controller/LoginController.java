package com.springboot.blog.controller;

import com.springboot.blog.dto.AuthenticationDto;
import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag( name = " Login Rest API")
public class LoginController {
    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // Login Rest API
    @Operation( summary = "User Login Rest API", description = "This Rest API authenticates the user login")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@RequestBody LoginDto loginDto){
        String token = loginService.login(loginDto);

        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setAccessToken(token);

        return ResponseEntity.ok(authenticationDto);
    }


}
