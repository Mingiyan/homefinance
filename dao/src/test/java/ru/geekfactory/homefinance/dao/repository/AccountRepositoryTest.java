package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.*;
import ru.geekfactory.homefinace.dao.model.AccountModel;
import ru.geekfactory.homefinace.dao.model.AccountType;
import ru.geekfactory.homefinace.dao.model.CurrencyModel;
import ru.geekfactory.homefinace.dao.repository.AccountRepository;
import ru.geekfactory.homefinace.dao.repository.RepositoryCRUD;

import java.math.BigDecimal;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountRepositoryTest {

    private static DatabaseConnectorTest databaseConnectorTest = new DatabaseConnectorTest();

    private AccountRepository accountRepository;


    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.initDB();
    }



    @BeforeEach
    void beforeEach() {
    }

    @Test
    public void testContext() {
        assertNotNull(accountRepository);
    }


    @Test
    @DisplayName("save and findById operation test")
    void saveAndFind() {
        CurrencyModel currencyNew = new CurrencyModel();
        currencyNew.setId((long) 1);
        currencyNew.setName("Dollar");
        AccountModel accountModel = new AccountModel();
        accountModel.setName("test");
        accountModel.setAccountType(AccountType.CASH);
        accountModel.setAmount(BigDecimal.valueOf(1));
        accountModel.setCurrency(currencyNew);
        accountRepository.save(accountModel);
        assertEquals(1, accountRepository.findById((long) 1).get().getId());
        assertEquals("test", accountRepository.findById((long) 1).get().getName());
    }
}
