package com.bank.command.commandhandler;

import com.bank.command.aggregate.BankAccountAggregate;
import com.bank.command.eventstore.EventStore;
import com.bank.domain.command.OpenAccountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAccountCommandHandler implements CommandHandler<OpenAccountCommand> {

    private final EventStore eventStore;

    @Override
    public void handle(OpenAccountCommand command) {
        // بررسی وجود حساب
        if (eventStore.exists(command.getAggregateId())) {
            throw new IllegalStateException("Account already exists with ID: " + command.getAggregateId());
        }

        BankAccountAggregate aggregate = new BankAccountAggregate();
        aggregate.openAccount(
                command.getAggregateId(),
                command.getAccountHolder(),
                command.getInitialBalance()
        );

        // ذخیره Eventها
        eventStore.saveEvents(command.getAggregateId(), aggregate.getChanges());
    }
}