package com.hackathon.finservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FundTransferRequest {

    private Double amount;
    private UUID targetAccountNumber;
}
