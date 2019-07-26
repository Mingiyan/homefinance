package dordzhiev.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private Collection<CategoryTransaction> categoryTransaction;
    private Account account;

}
