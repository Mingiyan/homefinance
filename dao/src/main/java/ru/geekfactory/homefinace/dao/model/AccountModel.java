package ru.geekfactory.homefinace.dao.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountModel {
    private Long id;
    private String name;
    private BigDecimal amount;
    private AccountType accountType;
    private CurrencyModel currency;
}
