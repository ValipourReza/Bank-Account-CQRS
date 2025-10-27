package com.bank.query.service;

import com.bank.domain.query.BankAccountResponse;
import com.bank.query.model.BankAccountView;
import com.bank.query.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountQueryService {

    private final BankAccountRepository bankAccountRepository;

    public List<BankAccountResponse> findAllAccounts() {
        return bankAccountRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public BankAccountResponse findAccountById(String accountId) {
        return bankAccountRepository.findByAccountId(accountId)
                .map(this::convertToResponse)
                .orElse(null);
    }

    public List<BankAccountResponse> findAccountsWithBalance(Double minBalance, Double maxBalance) {
        return bankAccountRepository.findByBalanceBetween(minBalance, maxBalance).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private BankAccountResponse convertToResponse(BankAccountView view) {
        return new BankAccountResponse(
                view.getAccountId(),
                view.getAccountHolder(),
                view.getBalance(),
                view.getStatus()
        );
    }
}