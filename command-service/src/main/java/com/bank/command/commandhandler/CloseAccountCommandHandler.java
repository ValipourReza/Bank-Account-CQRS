package com.bank.command.commandhandler;

import com.bank.command.aggregate.BankAccountAggregate;
import com.bank.command.eventstore.EventStore;
import com.bank.domain.command.CloseAccountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloseAccountCommandHandler implements CommandHandler<CloseAccountCommand> {

    private final EventStore eventStore;

    @Override
    public void handle(CloseAccountCommand command) {
        BankAccountAggregate aggregate = new BankAccountAggregate();

        var events = eventStore.getEvents(command.getAggregateId());
        aggregate.rebuildFromEvents(events);

        aggregate.closeAccount();

        eventStore.saveEvents(command.getAggregateId(), aggregate.getChanges());
    }
}