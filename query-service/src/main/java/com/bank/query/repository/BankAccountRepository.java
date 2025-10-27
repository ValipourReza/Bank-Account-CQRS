package com.bank.query.repository;

import com.bank.query.model.BankAccountView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccountView, String> {

    Optional<BankAccountView> findByAccountId(String accountId);

    List<BankAccountView> findByStatus(String status);

    List<BankAccountView> findByBalanceBetween(Double minBalance, Double maxBalance);
}