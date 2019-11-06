package ru.geekfactory.homefinance.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekfactory.homefinance.dao.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
