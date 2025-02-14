package com.hackathon.finservice.Controllers.dashboard;

import com.hackathon.finservice.DTO.AccountResponse;
import com.hackathon.finservice.DTO.UserAccountResponse;
import com.hackathon.finservice.DTO.UserInfoResponse;
import com.hackathon.finservice.Service.AccountService;
import com.hackathon.finservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/user")
    public ResponseEntity<UserInfoResponse> getUserInfo(Authentication authentication){
        return ResponseEntity.ok(userService.getUserInfo(authentication.getName()));
    }

    @GetMapping("/account")
    public ResponseEntity<UserAccountResponse> getAccountInfo(Authentication authentication){
            return ResponseEntity.ok(userService.getAccountInfo(authentication.getName()));
    }

    @GetMapping("/account/{index}")
    public ResponseEntity<AccountResponse> getAccountByIndex(@PathVariable int index, Authentication authentication) {
            return ResponseEntity.ok(
                    accountService.getAccountByIndex(
                            userService.getUserByEmail(authentication.getName()),
                            index
                    )
            );
    }


}
