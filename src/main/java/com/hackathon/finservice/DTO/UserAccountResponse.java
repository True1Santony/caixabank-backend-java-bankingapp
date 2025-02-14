package com.hackathon.finservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserAccountResponse {

    private UUID accountNumber;
    private Double balance;
    private String accountType;

    public UserAccountResponse() {
    }

    public UserAccountResponse(UUID accountNumber, Double balance, String accountType) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

}
