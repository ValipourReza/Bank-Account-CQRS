package com.bank.command.commandhandler;

import com.bank.command.aggregate.BankAccountAggregate;
import com.bank.command.eventstore.EventStore;
import com.bank.domain.command.WithdrawFundsCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawFundsCommandHandler implements CommandHandler<WithdrawFundsCommand> {

    private final EventStore eventStore;

    @Override
    public void handle(WithdrawFundsCommand command) {
        BankAccountAggregate aggregate = new BankAccountAggregate();

        var events = eventStore.getEvents(command.getAggregateId());
        aggregate.rebuildFromEvents(events);

        aggregate.withdrawFunds(command.getAmount());

        eventStore.saveEvents(command.getAggregateId(), aggregate.getChanges());
    }
}