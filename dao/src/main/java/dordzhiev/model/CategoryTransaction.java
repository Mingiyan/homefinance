package dordzhiev.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryTransaction {
    private Long id;
    private String name;
    private CategoryTransaction parentCategory;
}
