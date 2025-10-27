package com.bank.command.eventstore;

import com.bank.command.publisher.EventPublisher;
import com.bank.domain.event.BaseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final ObjectMapper objectMapper;
    private final EventPublisher eventPublisher; // اضافه شد

    public void saveEvents(String aggregateId, List<BaseEvent> events) {
        for (BaseEvent event : events) {
            try {
                String eventData = objectMapper.writeValueAsString(event);
                String eventType = event.getClass().getSimpleName();
                Long version = event.getVersion();

                EventEntity eventEntity = new EventEntity(aggregateId, eventType, eventData, version);
                eventStoreRepository.save(eventEntity);

                log.info("Event saved - Aggregate: {}, Type: {}, Version: {}",
                        aggregateId, eventType, version);

                // انتشار Event به Kafka
                eventPublisher.publish(event);

            } catch (JsonProcessingException e) {
                log.error("Error serializing event: {}", e.getMessage());
                throw new RuntimeException("Failed to serialize event", e);
            }
        }
    }

    public List<BaseEvent> getEvents(String aggregateId) {
        List<EventEntity> eventEntities = eventStoreRepository
                .findByAggregateIdOrderByVersionAsc(aggregateId);

        return eventEntities.stream()
                .map(this::convertToEvent)
                .collect(Collectors.toList());
    }

    public boolean exists(String aggregateId) {
        return eventStoreRepository.existsByAggregateId(aggregateId);
    }

    private BaseEvent convertToEvent(EventEntity eventEntity) {
        try {
            Class<?> eventClass = Class.forName("com.bank.domain.event." + eventEntity.getEventType());
            return (BaseEvent) objectMapper.readValue(eventEntity.getEventData(), eventClass);
        } catch (Exception e) {
            log.error("Error deserializing event: {}", e.getMessage());
            throw new RuntimeException("Failed to deserialize event", e);
        }
    }
}