package com.bank.query.controller;

import com.bank.domain.query.BankAccountResponse;
import com.bank.query.service.BankAccountQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class BankAccountQueryController {

    private final BankAccountQueryService bankAccountQueryService;

    @GetMapping
    public ResponseEntity<List<BankAccountResponse>> getAllAccounts() {
        List<BankAccountResponse> accounts = bankAccountQueryService.findAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<BankAccountResponse> getAccountById(@PathVariable String accountId) {
        BankAccountResponse account = bankAccountQueryService.findAccountById(accountId);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BankAccountResponse>> getAccountsWithBalance(
            @RequestParam Double minBalance,
            @RequestParam Double maxBalance) {

        List<BankAccountResponse> accounts = bankAccountQueryService
                .findAccountsWithBalance(minBalance, maxBalance);
        return ResponseEntity.ok(accounts);
    }
}