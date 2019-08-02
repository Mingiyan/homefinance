package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.*;
import ru.geekfactory.homefinace.dao.repository.AccountRepository;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll step.");
    }

    @BeforeEach
    void beforeEach() {
        accountRepository = new AccountRepository();
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
