package com.hackathon.finservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserInfoResponse {

    // Getters y Setters
    private String name;
    private String email;
    private UUID accountNumber;
    private String accountType;
    private String hashedPassword;

    // Constructor
    public UserInfoResponse(String name, String email, UUID accountNumber, String accountType, String hashedPassword) {
        this.name = name;
        this.email = email;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.hashedPassword = hashedPassword;
    }

}
