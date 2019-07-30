package ru.geekfactory.homefinace.dao.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryTransactionModel {
    private Long id;
    private String name;
    private CategoryTransactionModel parentCategory;

}
