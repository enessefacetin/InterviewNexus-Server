package com.enessefacetin.interviewnexus.controller;

import org.springframework.web.bind.annotation.RestController;

import com.enessefacetin.interviewnexus.model.request.AuthenticationRequest;
import com.enessefacetin.interviewnexus.model.request.RegisterRequest;
import com.enessefacetin.interviewnexus.model.response.AuthenticationResponse;
import com.enessefacetin.interviewnexus.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


    

}
