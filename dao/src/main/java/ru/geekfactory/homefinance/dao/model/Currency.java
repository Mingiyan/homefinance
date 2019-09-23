package ru.geekfactory.homefinance.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "currency_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    @Id
    private Long currencyId;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "currency")
    private Collection<Account> accounts;
}
