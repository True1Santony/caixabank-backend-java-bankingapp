package com.hackathon.finservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
public class TransactionResponse {
    private Long id;
    private Double amount;
    private String transactionType;
    private String transactionStatus;
    private Date transactionDate;
    private UUID sourceAccountNumber;
    private String targetAccountNumber;// string para mostrar N/A si no hay cuenta de envio

}