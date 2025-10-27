package com.bank.query.eventhandler;

import com.bank.domain.event.*;
import com.bank.query.model.BankAccountView;
import com.bank.query.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountEventHandler {

    private final BankAccountRepository bankAccountRepository;

    @KafkaListener(topics = "bank-account-events", containerFactory = "kafkaListenerContainerFactory")
    public void handleEvent(@Payload BaseEvent event) {
        log.info("Received event: {} for aggregate: {}",
                event.getClass().getSimpleName(), event.getAggregateId());

        if (event instanceof AccountOpenedEvent) {
            handle((AccountOpenedEvent) event);
        } else if (event instanceof FundsDepositedEvent) {
            handle((FundsDepositedEvent) event);
        } else if (event instanceof FundsWithdrawnEvent) {
            handle((FundsWithdrawnEvent) event);
        } else if (event instanceof AccountClosedEvent) {
            handle((AccountClosedEvent) event);
        } else {
            log.warn("Unknown event type: {}", event.getClass().getName());
        }
    }

    // بقیه متدها بدون تغییر...
    private void handle(AccountOpenedEvent event) {
        BankAccountView accountView = new BankAccountView(
                event.getAggregateId(),
                event.getAccountHolder(),
                event.getInitialBalance(),
                "OPEN"
        );
        bankAccountRepository.save(accountView);
        log.info("Account created in read database: {}", event.getAggregateId());
    }

    private void handle(FundsDepositedEvent event) {
        bankAccountRepository.findByAccountId(event.getAggregateId())
                .ifPresent(account -> {
                    account.setBalance(event.getBalanceAfterDeposit());
                    bankAccountRepository.save(account);
                    log.info("Account balance updated after deposit: {}", event.getAggregateId());
                });
    }

    private void handle(FundsWithdrawnEvent event) {
        bankAccountRepository.findByAccountId(event.getAggregateId())
                .ifPresent(account -> {
                    account.setBalance(event.getBalanceAfterWithdrawal());
                    bankAccountRepository.save(account);
                    log.info("Account balance updated after withdrawal: {}", event.getAggregateId());
                });
    }

    private void handle(AccountClosedEvent event) {
        bankAccountRepository.findByAccountId(event.getAggregateId())
                .ifPresent(account -> {
                    account.setStatus("CLOSED");
                    bankAccountRepository.save(account);
                    log.info("Account closed in read database: {}", event.getAggregateId());
                });
    }
}