package ru.geekfactory.homefinance.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekfactory.homefinance.dao.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
