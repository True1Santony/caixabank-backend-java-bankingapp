package com.hackathon.finservice.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Token() {}


}
