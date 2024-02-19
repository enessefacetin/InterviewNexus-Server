package com.enessefacetin.interviewnexus.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enessefacetin.interviewnexus.model.entity.Role;
import com.enessefacetin.interviewnexus.model.entity.User;
import com.enessefacetin.interviewnexus.model.request.AuthenticationRequest;
import com.enessefacetin.interviewnexus.model.request.RegisterRequest;
import com.enessefacetin.interviewnexus.model.response.AuthenticationResponse;
import com.enessefacetin.interviewnexus.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.User)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
       
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail()).get();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
