package ru.geekfactory.homefinance.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekfactory.homefinance.dao.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
