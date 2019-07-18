package ru.dordzhiev.model;

import java.time.LocalDateTime;

public class Transaction {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private CategoryTransaction categoryTransaction;
}
