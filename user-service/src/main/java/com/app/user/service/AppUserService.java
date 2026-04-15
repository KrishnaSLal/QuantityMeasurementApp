package com.app.user.service;

import com.app.user.entity.AppUser;
import com.app.user.repository.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepository repository;

    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
    }

    public AppUser saveOrGetUser(String name, String email) {
        return repository.findByEmail(email)
                .orElseGet(() -> {
                    AppUser user = new AppUser();
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword("OAUTH_USER");
                    user.setRole("USER");
                    return repository.save(user);
                });
    }
}