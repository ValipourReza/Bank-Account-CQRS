package com.bank.domain.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenAccountCommand extends BaseCommand {

    @NotBlank(message = "Account holder is required")
    private String accountHolder;

    @NotNull(message = "Initial balance is required")
    @Min(value = 0, message = "Initial balance must be positive")
    private Double initialBalance;

    public OpenAccountCommand(String aggregateId, String accountHolder, Double initialBalance) {
        super(aggregateId);
        this.accountHolder = accountHolder;
        this.initialBalance = initialBalance;
    }
}