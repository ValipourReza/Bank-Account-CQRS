package com.bank.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FundsDepositedEvent extends BaseEvent {
    private Double amount;
    private Double balanceAfterDeposit;

    public FundsDepositedEvent(String aggregateId, Double amount, Double balanceAfterDeposit) {
        super(aggregateId);
        this.amount = amount;
        this.balanceAfterDeposit = balanceAfterDeposit;
    }
}