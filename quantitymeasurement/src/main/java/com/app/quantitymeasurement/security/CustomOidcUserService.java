package com.app.quantitymeasurement.security;

import org.springframework.security.oauth2.client.oidc.userinfo.*;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.service.AppUserService;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final AppUserService userService;

    public CustomOidcUserService(AppUserService userService) {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest request) {
        OidcUser user = super.loadUser(request);

        userService.saveOrGetUser(
                user.getFullName(),
                user.getEmail()
        );

        return user;
    }
}