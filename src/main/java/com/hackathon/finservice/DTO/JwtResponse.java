package com.hackathon.finservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

}
