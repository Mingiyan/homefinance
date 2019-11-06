package ru.geekfactory.homefinance.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekfactory.homefinance.dao.model.CategoryTransaction;

public interface CategoryTransactionRepository extends JpaRepository<CategoryTransaction, Long> {
}
