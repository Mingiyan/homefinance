package dordzhiev.model;

import java.time.LocalDateTime;

public class Transaction {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private CategoryTransaction categoryTransaction;
    private AccountType accountType;
    private Currency currency;

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public CategoryTransaction getCategoryTransaction() {
        return categoryTransaction;
    }

    public void setCategoryTransaction(CategoryTransaction categoryTransaction) {
        this.categoryTransaction = categoryTransaction;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", categoryTransaction=" + categoryTransaction +
                ", accountType=" + accountType +
                ", currency=" + currency +
                '}';
    }
}
