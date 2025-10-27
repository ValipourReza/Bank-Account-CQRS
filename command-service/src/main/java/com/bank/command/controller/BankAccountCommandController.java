package com.bank.command.controller;

import com.bank.command.commandhandler.*;
import com.bank.domain.command.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class BankAccountCommandController {

    private final OpenAccountCommandHandler openAccountHandler;
    private final DepositFundsCommandHandler depositFundsHandler;
    private final WithdrawFundsCommandHandler withdrawFundsHandler;
    private final CloseAccountCommandHandler closeAccountHandler;

    @PostMapping
    public ResponseEntity<String> openAccount(@RequestBody OpenAccountCommand command) {
        openAccountHandler.handle(command);
        return ResponseEntity.ok("Account opened successfully - ID: " + command.getAggregateId());
    }

    @PutMapping("/{accountId}/deposit")
    public ResponseEntity<String> depositFunds(
            @PathVariable String accountId,
            @RequestBody DepositFundsCommand command) {

        command.setAggregateId(accountId);
        depositFundsHandler.handle(command);
        return ResponseEntity.ok("Funds deposited to account: " + accountId);
    }

    @PutMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdrawFunds(
            @PathVariable String accountId,
            @RequestBody WithdrawFundsCommand command) {

        command.setAggregateId(accountId);
        withdrawFundsHandler.handle(command);
        return ResponseEntity.ok("Funds withdrawn from account: " + accountId);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> closeAccount(@PathVariable String accountId) {
        CloseAccountCommand command = new CloseAccountCommand(accountId);
        closeAccountHandler.handle(command);
        return ResponseEntity.ok("Account closed: " + accountId);
    }
}