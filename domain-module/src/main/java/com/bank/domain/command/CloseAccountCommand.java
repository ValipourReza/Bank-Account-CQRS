package com.bank.domain.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand(String aggregateId) {
        super(aggregateId);
    }
}