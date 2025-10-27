package com.bank.domain.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountResponse {
    private String accountId;
    private String accountHolder;
    private Double balance;
    private String status; // OPEN, CLOSED
}