package dordzhiev.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {
    private Long id;
    private String name;
    private BigDecimal amount;
    private AccountType accountType;
    private Currency currency;
}
