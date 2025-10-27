package com.bank.domain.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OpenAccountCommand.class, name = "OPEN_ACCOUNT"),
        @JsonSubTypes.Type(value = DepositFundsCommand.class, name = "DEPOSIT_FUNDS"),
        @JsonSubTypes.Type(value = WithdrawFundsCommand.class, name = "WITHDRAW_FUNDS"),
        @JsonSubTypes.Type(value = CloseAccountCommand.class, name = "CLOSE_ACCOUNT")
})
public abstract class BaseCommand {
    private String aggregateId; // accountId

    public BaseCommand(String aggregateId) {
        this.aggregateId = aggregateId;
    }
}