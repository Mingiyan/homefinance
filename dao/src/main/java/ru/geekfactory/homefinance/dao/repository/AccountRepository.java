package ru.geekfactory.homefinance.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekfactory.homefinance.dao.model.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
}
