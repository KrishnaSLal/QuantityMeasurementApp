package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.AppUser;
import com.app.quantitymeasurement.repository.AppUserRepository;
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
                    user.setRole("USER");
                    return repository.save(user);
                });
    }
}