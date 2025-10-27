package com.bank.command.eventstore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_store")
@Getter
@Setter
@NoArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String aggregateId;

    @Column(nullable = false)
    private String eventType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String eventData;

    @Column(nullable = false)
    private Long version;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public EventEntity(String aggregateId, String eventType, String eventData, Long version) {
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.eventData = eventData;
        this.version = version;
        this.timestamp = LocalDateTime.now();
    }
}