package ru.geekfactory.homefinance.dao.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {
    private Long id;
    private String name;
    private BigDecimal amount;
    private AccountType accountType;
    private CurrencyModel currency;
}