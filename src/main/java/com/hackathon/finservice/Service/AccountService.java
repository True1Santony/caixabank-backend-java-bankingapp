package com.hackathon.finservice.Service;

import com.hackathon.finservice.DTO.AccountResponse;
import com.hackathon.finservice.Entities.Account;
import com.hackathon.finservice.Entities.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public AccountResponse getAccountByIndex(User user, int index) {

        if (index < 0 || index >= user.getAccounts().size()) {
            throw new IllegalArgumentException("Account index out of range");
        }
        Account account = user.getAccounts().get(index);

        return new AccountResponse(
                account.getAccountNumber(),
                account.getBalance(),
                account.getAccountType()
        );
    }
}
