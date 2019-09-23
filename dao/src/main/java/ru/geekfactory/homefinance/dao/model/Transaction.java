package ru.geekfactory.homefinance.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "transaction_tbl")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private String transactionId;

    @Column(name = "name")
    private String name;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToMany(mappedBy = "transactions", fetch = FetchType.LAZY)
    private Collection<CategoryTransaction> categoryTransactions;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
}
