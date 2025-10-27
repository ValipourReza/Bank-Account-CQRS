package com.bank.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountOpenedEvent extends BaseEvent {
    private String accountHolder;
    private Double initialBalance;

    public AccountOpenedEvent(String aggregateId, String accountHolder, Double initialBalance) {
        super(aggregateId);
        this.accountHolder = accountHolder;
        this.initialBalance = initialBalance;
    }
}