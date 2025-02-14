package com.hackathon.finservice.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)  // número de cuenta único
    private UUID accountNumber;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    private Double balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {}

    public Account(User user) {
        this.user = user;
        this.accountNumber = UUID.randomUUID();
        this.accountType = "Main";
        this.balance =  0.0;
    }

}
