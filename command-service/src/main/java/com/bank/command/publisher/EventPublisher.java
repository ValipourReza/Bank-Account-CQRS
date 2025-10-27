package com.bank.command.publisher;

import com.bank.domain.event.BaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String BANK_ACCOUNT_EVENTS_TOPIC = "bank-account-events";

    public void publish(BaseEvent event) {
        try {
            kafkaTemplate.send(BANK_ACCOUNT_EVENTS_TOPIC, event.getAggregateId(), event)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Event published successfully - Aggregate: {}, Type: {}, Offset: {}",
                                    event.getAggregateId(),
                                    event.getClass().getSimpleName(),
                                    result.getRecordMetadata().offset());
                        } else {
                            log.error("Failed to publish event - Aggregate: {}, Error: {}",
                                    event.getAggregateId(), ex.getMessage());
                        }
                    });
        } catch (Exception e) {
            log.error("Error publishing event to Kafka: {}", e.getMessage());
        }
    }
}