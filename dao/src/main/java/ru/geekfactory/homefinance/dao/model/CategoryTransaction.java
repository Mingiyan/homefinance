package ru.geekfactory.homefinance.dao.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "category_tbl")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"categories", "transactions"})
public class CategoryTransaction {

    @Id
    @GeneratedValue
    private Long categoryId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private CategoryTransaction parent;

    @OneToMany(mappedBy = "parent")
    private Collection<CategoryTransaction> categories;

    @ManyToMany(mappedBy = "categoryTransactions", fetch = FetchType.LAZY)
    private Collection<Transaction> transactions;
}
