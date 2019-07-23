package ru.dordzhiev.model;

import java.math.BigDecimal;
import java.util.Optional;

public class Account {
    private Long id;
    private String name;
    private BigDecimal amount;
    private AccountType accountType;
    private Optional<Currency> currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Optional<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(Optional<Currency> currency) {
        this.currency = currency;
    }

    public Account(){
    }

    public Account(String name, BigDecimal amount, AccountType accountType, Currency currency) {
        this.name = name;
        this.amount = amount;
        this.accountType = accountType;
        this.currency = Optional.ofNullable(currency);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", accountType=" + accountType +
                ", currency=" + currency +
                '}';
    }
}
