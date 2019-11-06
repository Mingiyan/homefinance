package ru.geekfactory.homefinance.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@Entity
@Table(name = "account_tbl")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "transactions")
public class Account {

    @Id
    @GeneratedValue
    private Long accountId;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "currencyId")
    private Currency currency;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Collection<Transaction> transactions;
}
