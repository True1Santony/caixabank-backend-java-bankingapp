package com.hackathon.finservice.Service;

import com.hackathon.finservice.DTO.*;
import com.hackathon.finservice.Entities.Account;
import com.hackathon.finservice.Entities.User;
import com.hackathon.finservice.Repositories.AccountRepository;
import com.hackathon.finservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserRegistrationResponse registerUser(UserRegistrationRequest request){

        validateEmail(request.getEmail());

        String password = request.getPassword();

            if (password.length() < 8) {
                throw new IllegalArgumentException("Password must be at least 8 characters long");
            }
            if (password.length() > 128) {
                throw new IllegalArgumentException("Password must be less than 128 characters long");
            }
            if (password.contains(" ")) {
                throw new IllegalArgumentException("Password cannot contain whitespace");
            }
            if (!password.matches(".*[A-Z].*")) {
                throw new IllegalArgumentException("Password must contain at least one uppercase letter");
            }

            boolean hasDigit = password.matches(".*\\d.*");
            boolean hasSpecialChar = password.matches(".*[@#$%^&+=!?.].*");

            if (!hasDigit && !hasSpecialChar) {
                throw new IllegalArgumentException("Password must contain at least one digit and one special character");
            }

            if (!hasSpecialChar) {
                throw new IllegalArgumentException("Password must contain at least one special character");
            }

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new DuplicateKeyException(null);
        }

        User user = new User(request.getName(), request.getEmail(),passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        Account account = new Account(savedUser);
        accountRepository.save(account);

        return new UserRegistrationResponse(
                savedUser.getName(),
                savedUser.getEmail(),
                account.getAccountNumber(),
                account.getAccountType(),
                savedUser.getHashedPassword()
        );

    }

    public UserInfoResponse getUserInfo(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found for email: "+ email);
        }
        User user = userOptional.get();

        Account mainAccount = getMainAccount(user);

        return new UserInfoResponse(
                user.getName(),
                user.getEmail(),
                mainAccount.getAccountNumber(),
                mainAccount.getAccountType(),
                user.getHashedPassword()
        );
    }

    public UserAccountResponse getAccountInfo(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found for email: "+ email);
        }
        User user = userOptional.get();

        Account mainAccount = getMainAccount(user);

        return new UserAccountResponse (
                mainAccount.getAccountNumber(),
                mainAccount.getBalance(),
                mainAccount.getAccountType()
        );
    }

    public Account getMainAccount(User user) {
        return user.getAccounts().stream()
                .filter(account -> "Main".equals(account.getAccountType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Main account not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found for the given identifier: "+ email)
        );
    }

    public void addAccountToUser(User user, AccountRequest accountRequest) {

        UUID cuenta = getMainAccount(user).getAccountNumber();

        if (!cuenta.equals(accountRequest.getAccountNumber())) {
            throw new BadCredentialsException("Account number does not match main account");
        }

        Account newAccount = new Account(user);

        newAccount.setAccountType(accountRequest.getAccountType());

        accountRepository.save(newAccount);

        user.getAccounts().add(newAccount);
        userRepository.save(user);

    }

    private void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }

}
