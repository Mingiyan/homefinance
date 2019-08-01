package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.BeforeAll;
import ru.geekfactory.homefinace.dao.model.AccountModel;
import ru.geekfactory.homefinace.dao.repository.RepositoryCRUD;

public class AccountRepositoryTest {

    private RepositoryCRUD<Long, AccountModel> accountModel;

    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll step.");
    }
}
