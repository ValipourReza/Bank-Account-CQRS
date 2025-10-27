package com.bank.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FundsWithdrawnEvent extends BaseEvent {
    private Double amount;
    private Double balanceAfterWithdrawal;

    public FundsWithdrawnEvent(String aggregateId, Double amount, Double balanceAfterWithdrawal) {
        super(aggregateId);
        this.amount = amount;
        this.balanceAfterWithdrawal = balanceAfterWithdrawal;
    }
}