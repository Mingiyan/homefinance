package ru.geekfactory.homefinace.dao.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private Collection<CategoryTransactionModel> categoryTransaction;
    private AccountModel account;
}
