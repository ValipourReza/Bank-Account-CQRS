package com.bank.command.eventstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStoreRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByAggregateIdOrderByVersionAsc(String aggregateId);

    boolean existsByAggregateId(String aggregateId);
}