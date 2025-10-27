package com.bank.command.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic bankAccountEventsTopic() {
        return TopicBuilder.name("bank-account-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
}