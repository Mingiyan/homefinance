package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.*;
import ru.geekfactory.homefinace.dao.repository.AccountRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountRepositoryTest {

    private DatabaseConnectorTest databaseConnectorTest;
    private AccountRepository accountRepository = new AccountRepository();

    @BeforeAll
    static void beforeAll(DatabaseConnectorTest databaseConnectorTest) {
        databaseConnectorTest.initDB();
    }


    @BeforeEach
    void beforeEach() {
        databaseConnectorTest = new DatabaseConnectorTest();
    }

    @Test
    public void testContext() {
        assertNotNull(accountRepository);
    }


    @Test
    @DisplayName("save and findById operation test")
    void saveAndFind() {
//        accountRepository.save();
    }
}
