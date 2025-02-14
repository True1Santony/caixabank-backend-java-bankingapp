package com.hackathon.finservice.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class TransactionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;//PENDING(DEFAULT), APPROVED, FRAUD

    @OneToOne(mappedBy = "transactionStatus")
    private Transaction transaction;

    public TransactionStatus() {this.status = Status.PENDING;}

    public TransactionStatus(Status status){
        this.status=status;
    }

    public enum Status {
        PENDING, APPROVED, FRAUD
    }

}
