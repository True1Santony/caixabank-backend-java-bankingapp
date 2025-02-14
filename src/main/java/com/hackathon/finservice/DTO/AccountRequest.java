package com.hackathon.finservice.DTO;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class AccountRequest {

    // Getters y setters
    @NotNull
    private UUID accountNumber;
    @NotNull
    private String accountType;

}