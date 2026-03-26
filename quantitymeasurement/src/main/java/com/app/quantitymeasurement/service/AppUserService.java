package com.app.quantitymeasurement.service;

import org.springframework.stereotype.Service;
import com.app.quantitymeasurement.entity.AppUser;
import com.app.quantitymeasurement.repository.AppUserRepository;

@Service
public class AppUserService {

    private final AppUserRepository repository;

    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
    }

    public AppUser saveOrGetUser(String name, String email) {
        return repository.findByEmail(email)
                .orElseGet(() -> repository.save(new AppUser(name, email, "USER")));
    }
}