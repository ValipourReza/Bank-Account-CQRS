package com.bank.query.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "bank_accounts")
public class BankAccountView {

    @Id
    private String accountId;

    private String accountHolder;
    private Double balance;
    private String status; // OPEN, CLOSED

    public BankAccountView(String accountId, String accountHolder, Double balance, String status) {
        this.accountId = accountId;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.status = status;
    }
}