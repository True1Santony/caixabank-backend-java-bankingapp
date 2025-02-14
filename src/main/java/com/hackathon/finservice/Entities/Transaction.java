package com.hackathon.finservice.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Setter
@Getter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_account_id", nullable = true)
    private Account senderAccount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_account_id", nullable = true)
    private Account receiverAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_status_id", nullable = false)
    private TransactionStatus transactionStatus;

    @Column(nullable = false)
    private Double amount;

    @Column
    private Double effectiveAmount;

    @Column
    private Double balanceBefore;  // Saldo antes del depósito

    @Column
    private Double balanceAfter;   // Saldo después del depósito

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)  // Esta columna es la que conecta con Account
    private Account account;

    public Transaction() {}

    public Transaction(Account senderAccount, Account receiverAccount,
                       TransactionType transactionType, TransactionStatus transactionStatus,
                       Double amount, Double effectiveAmount, Double balanceBefore, Double balanceAfter) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.effectiveAmount = effectiveAmount;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", senderAccount=" + senderAccount +
                ", receiverAccount=" + receiverAccount +
                ", transactionType=" + transactionType +
                ", transactionStatus=" + transactionStatus +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", account=" + account +
                '}';
    }
}
