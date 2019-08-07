package ru.geekfactory.homefinance.dao.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTransactionModel {
    private Long id;
    private String name;
    private CategoryTransactionModel parentCategory;
}
