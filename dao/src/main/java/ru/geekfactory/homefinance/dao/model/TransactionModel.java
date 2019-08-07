package ru.geekfactory.homefinance.dao.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private Collection<CategoryTransactionModel> categoryTransaction;
    private AccountModel account;
}
