package com.app.user.security;

import com.app.user.service.AppUserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class OAuthUserService extends OidcUserService {

    private final AppUserService appUserService;

    public OAuthUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser user = super.loadUser(userRequest);
        appUserService.saveOrGetUser(user.getFullName(), user.getEmail());
        return user;
    }
}