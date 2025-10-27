package com.bank.command.aggregate;

import com.bank.domain.event.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BankAccountAggregate {
    private String accountId;
    private String accountHolder;
    private Double balance;
    private String status; // OPEN, CLOSED
    private final List<BaseEvent> changes = new ArrayList<>();

    public BankAccountAggregate() {}

    // بازیابی state از Eventها
    public void rebuildFromEvents(List<BaseEvent> events) {
        for (BaseEvent event : events) {
            applyEvent(event, false);
        }
    }

    // Command: افتتاح حساب
    public void openAccount(String accountId, String accountHolder, Double initialBalance) {
        if (this.accountId != null) {
            throw new IllegalStateException("Account already exists");
        }

        AccountOpenedEvent event = new AccountOpenedEvent(accountId, accountHolder, initialBalance);
        applyEvent(event, true);
    }

    // Command: واریز وجه
    public void depositFunds(Double amount) {
        validateAccountOpen();
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Double newBalance = this.balance + amount;
        FundsDepositedEvent event = new FundsDepositedEvent(accountId, amount, newBalance);
        applyEvent(event, true);
    }

    // Command: برداشت وجه
    public void withdrawFunds(Double amount) {
        validateAccountOpen();
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (this.balance < amount) {
            throw new IllegalStateException("Insufficient funds");
        }

        Double newBalance = this.balance - amount;
        FundsWithdrawnEvent event = new FundsWithdrawnEvent(accountId, amount, newBalance);
        applyEvent(event, true);
    }

    // Command: بستن حساب
    public void closeAccount() {
        validateAccountOpen();

        AccountClosedEvent event = new AccountClosedEvent(accountId);
        applyEvent(event, true);
    }

    // اعمال Event روی State
    private void applyEvent(BaseEvent event, boolean isNew) {
        if (event instanceof AccountOpenedEvent) {
            apply((AccountOpenedEvent) event);
        } else if (event instanceof FundsDepositedEvent) {
            apply((FundsDepositedEvent) event);
        } else if (event instanceof FundsWithdrawnEvent) {
            apply((FundsWithdrawnEvent) event);
        } else if (event instanceof AccountClosedEvent) {
            apply((AccountClosedEvent) event);
        }

        if (isNew) {
            changes.add(event);
        }
    }

    // Event Handlers
    private void apply(AccountOpenedEvent event) {
        this.accountId = event.getAggregateId();
        this.accountHolder = event.getAccountHolder();
        this.balance = event.getInitialBalance();
        this.status = "OPEN";
    }

    private void apply(FundsDepositedEvent event) {
        this.balance = event.getBalanceAfterDeposit();
    }

    private void apply(FundsWithdrawnEvent event) {
        this.balance = event.getBalanceAfterWithdrawal();
    }

    private void apply(AccountClosedEvent event) {
        this.status = "CLOSED";
    }

    private void validateAccountOpen() {
        if (!"OPEN".equals(this.status)) {
            throw new IllegalStateException("Account is not open");
        }
    }
}