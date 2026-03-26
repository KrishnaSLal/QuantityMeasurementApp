package com.app.quantitymeasurement.config;

import com.app.quantitymeasurement.security.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomOidcUserService oidcService;
    private final OAuthSuccessHandler successHandler;

    public SecurityConfig(CustomOidcUserService oidcService,
                          OAuthSuccessHandler successHandler) {
        this.oidcService = oidcService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/error").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(user -> user.oidcUserService(oidcService))
                .successHandler(successHandler)
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}