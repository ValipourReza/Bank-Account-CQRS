package com.bank.domain.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindAccountsWithBalanceQuery extends BaseQuery {
    private Double minBalance;
    private Double maxBalance;
}