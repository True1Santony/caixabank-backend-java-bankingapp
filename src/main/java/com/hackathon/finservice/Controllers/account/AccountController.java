package com.hackathon.finservice.Controllers.account;

import com.hackathon.finservice.DTO.*;
import com.hackathon.finservice.Entities.User;
import com.hackathon.finservice.Service.TransactionService;
import com.hackathon.finservice.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<String> createNewAccount(@RequestBody @Valid AccountRequest accountRequest, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        userService.addAccountToUser(userService.getUserByEmail(authentication.getName()), accountRequest);
        return ResponseEntity.ok("New account added successfully for user");
    }

    @PostMapping("/deposit")
    public ResponseEntity<Map<String, String>> depositInAccount(@RequestBody DepositRequest depositRequest, Authentication authentication) {
            User user = userService.getUserByEmail(authentication.getName());
            transactionService.deposit(depositRequest.getAmount(), user);
            return ResponseEntity.ok(Map.of("msg", "Cash deposited successfully"));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Map<String, String>> withdrawFromAccount(@RequestBody WithdrawRequest withdrawRequest, Authentication authentication) {
        transactionService.withdraw(withdrawRequest.getAmount(), userService.getUserByEmail(authentication.getName()));
        return ResponseEntity.ok(Map.of("msg", "Cash withdrawn successfully"));
    }

    @PostMapping("/fund-transfer")
    public ResponseEntity<Map<String, String>> transferFunds(@RequestBody FundTransferRequest fundTransferRequest, Authentication authentication) {
        transactionService.transferFunds(
                    fundTransferRequest.getAmount(),
                    fundTransferRequest.getTargetAccountNumber(),
                    userService.getUserByEmail(authentication.getName())
            );
        return ResponseEntity.ok(Map.of("msg", "Fund transferred successfully"));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(Authentication authentication) {
        return ResponseEntity.ok(
                transactionService.getTransactionHistory(
                        userService.getUserByEmail(authentication.getName())
                )
        );

    }

}
