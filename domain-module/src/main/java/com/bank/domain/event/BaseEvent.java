package com.bank.domain.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "eventType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AccountOpenedEvent.class, name = "ACCOUNT_OPENED"),
        @JsonSubTypes.Type(value = FundsDepositedEvent.class, name = "FUNDS_DEPOSITED"),
        @JsonSubTypes.Type(value = FundsWithdrawnEvent.class, name = "FUNDS_WITHDRAWN"),
        @JsonSubTypes.Type(value = AccountClosedEvent.class, name = "ACCOUNT_CLOSED")
})
public abstract class BaseEvent {
    private String aggregateId;
    private Long version;
    private LocalDateTime timestamp;

    public BaseEvent(String aggregateId) {
        this.aggregateId = aggregateId;
        this.timestamp = LocalDateTime.now();
        this.version = 1L;
    }
}