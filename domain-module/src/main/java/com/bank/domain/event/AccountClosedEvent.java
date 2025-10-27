package com.bank.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountClosedEvent extends BaseEvent {

    public AccountClosedEvent(String aggregateId) {
        super(aggregateId);
    }
}