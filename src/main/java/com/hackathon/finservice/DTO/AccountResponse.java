package com.hackathon.finservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class AccountResponse {

    // Getters y setters
    private UUID accountNumber;
    private Double balance;
    private String accountType;

    // Constructor
    public AccountResponse(UUID accountNumber, Double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
    }

}