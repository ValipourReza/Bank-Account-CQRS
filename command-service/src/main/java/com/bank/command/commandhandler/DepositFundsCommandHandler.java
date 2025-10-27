package com.bank.command.commandhandler;

import com.bank.command.aggregate.BankAccountAggregate;
import com.bank.command.eventstore.EventStore;
import com.bank.domain.command.DepositFundsCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositFundsCommandHandler implements CommandHandler<DepositFundsCommand> {

    private final EventStore eventStore;

    @Override
    public void handle(DepositFundsCommand command) {
        BankAccountAggregate aggregate = new BankAccountAggregate();

        // بازیابی state از Eventهای قبلی
        var events = eventStore.getEvents(command.getAggregateId());
        aggregate.rebuildFromEvents(events);

        // اجرای Command
        aggregate.depositFunds(command.getAmount());

        // ذخیره Eventهای جدید
        eventStore.saveEvents(command.getAggregateId(), aggregate.getChanges());
    }
}