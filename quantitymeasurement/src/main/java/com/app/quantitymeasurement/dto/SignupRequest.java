package com.app.quantitymeasurement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String fullName;
    private String email;
    private String password;
    private String mobileNumber;
}