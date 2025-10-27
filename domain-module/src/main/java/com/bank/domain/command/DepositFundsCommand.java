package com.bank.domain.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepositFundsCommand extends BaseCommand {

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be positive")
    private Double amount;

    public DepositFundsCommand(String aggregateId, Double amount) {
        super(aggregateId);
        this.amount = amount;
    }
}