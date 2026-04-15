package com.app.user.config;

import com.app.user.security.OAuthSuccessHandler;
import com.app.user.security.OAuthUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    private final OAuthUserService oauthUserService;
    private final OAuthSuccessHandler successHandler;

    public SecurityConfig(OAuthUserService oauthUserService,
                          OAuthSuccessHandler successHandler) {
        this.oauthUserService = oauthUserService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/v1/auth/**",
                    "/oauth2/**",
                    "/login",
                    "/error"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(user -> user.oidcUserService(oauthUserService))
                .successHandler(successHandler)
            );

        return http.build();
    }
}