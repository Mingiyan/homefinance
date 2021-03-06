package ru.geekfactory.homefinance.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "transaction_tbl")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "categoryTransactions")
public class Transaction {

    @Id
    @GeneratedValue
    private Long transactionId;

    @Column(name = "name")
    private String name;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<CategoryTransaction> categoryTransactions;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @Column(name = "amount")
    private BigDecimal amount;
}
