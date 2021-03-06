package ru.geekfactory.homefinance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvertedCurrency {

    private Long currencyId;

    private String name;
}
