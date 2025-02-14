package com.hackathon.finservice.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)  // Esta anotación asegura que se guardará como un String en la base de datos
    @Column(nullable = false)
    private TransactionTypeEnum type;

    @OneToOne(mappedBy = "transactionType")
    private Transaction transaction;

    public TransactionType() {}

    public TransactionType(TransactionTypeEnum type) {
        this.type = type;
    }

    public enum TransactionTypeEnum {
        CASH_DEPOSIT,
        CASH_WITHDRAWAL,
        CASH_TRANSFER;
    }

}
