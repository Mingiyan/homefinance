package ru.geekfactory.homefinance.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "category_tbl")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTransaction {

    @Id
    private Long categoryId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private CategoryTransaction parent;

    @OneToMany(mappedBy = "parent")
    private Collection<CategoryTransaction> categories;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Transaction> transactions;
}