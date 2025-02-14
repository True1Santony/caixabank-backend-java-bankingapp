package com.hackathon.finservice.Service;

import com.hackathon.finservice.Entities.Token;
import com.hackathon.finservice.Entities.User;
import com.hackathon.finservice.Repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Token saveToken(User user, String jwt) {
        Token token = new Token();
        token.setToken(jwt);
        token.setUser(user);
        token.setActive(true); // El token es activo por defecto
        return tokenRepository.save(token);
    }

    public void revokeAllTokensByUser(User user) {
        List<Token> activeTokens = tokenRepository.findByUserIdAndActive(user.getId(), true);
        for (Token token : activeTokens) {
            token.setActive(false);  // Inactivar todos los tokens activos
            tokenRepository.save(token);
        }
    }
}
