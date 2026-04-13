package com.app.quantitymeasurement.config;

import com.app.quantitymeasurement.security.OAuthAuthenticationFailureHandler;
import com.app.quantitymeasurement.security.OAuthSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final OAuthAuthenticationFailureHandler oidcService;
    private final OAuthSuccessHandler successHandler;

    public SecurityConfig(OAuthAuthenticationFailureHandler oidcService,
                          OAuthSuccessHandler successHandler) {
        this.oidcService = oidcService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/error",
                    "/login",
                    "/oauth2/**",
                    "/h2-console/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api/v1/auth/**",
                    "/api/v1/quantities/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(user -> user.oidcUserService(oidcService))
                .successHandler(successHandler)
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
            );

        return http.build();
    }
}