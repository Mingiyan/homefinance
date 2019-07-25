package dordzhiev.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private CategoryTransaction categoryTransaction;
    private AccountType accountType;
    private Currency currency;
}
