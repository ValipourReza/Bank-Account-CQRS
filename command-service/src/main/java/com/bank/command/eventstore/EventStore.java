package com.bank.command.eventstore;

import com.bank.domain.event.BaseEvent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EventStore {

    private final Map<String, List<BaseEvent>> eventStore = new ConcurrentHashMap<>();

    public void saveEvents(String aggregateId, List<BaseEvent> events) {
        eventStore.computeIfAbsent(aggregateId, k -> new ArrayList<>())
                .addAll(events);
    }

    public List<BaseEvent> getEvents(String aggregateId) {
        return eventStore.getOrDefault(aggregateId, new ArrayList<>());
    }

    public boolean exists(String aggregateId) {
        return eventStore.containsKey(aggregateId);
    }
}