package com.enessefacetin.interviewnexus.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter{

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain) throws ServletException, IOException {

            final String authorizationHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            } else {
                jwt = authorizationHeader.substring(7);
                userEmail = jwtService.extractUserName(jwt);
                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request, response);
            }
    }
}
