package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geekfactory.homefinace.dao.model.AccountModel;
import ru.geekfactory.homefinace.dao.model.AccountType;
import ru.geekfactory.homefinace.dao.model.CurrencyModel;
import ru.geekfactory.homefinace.dao.repository.AccountRepository;
import ru.geekfactory.homefinace.dao.repository.CurrencyRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountRepositoryTest {

    private static DatabaseConnectorTest databaseConnectorTest = new DatabaseConnectorTest();

    private AccountRepository accountRepository = new AccountRepository();
    private CurrencyRepository currencyRepository = new CurrencyRepository();


    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
        databaseConnectorTest.initDB();
    }

    @BeforeEach
    void beforeEach() {
//        databaseConnectorTest.getConnection();
    }

    @Test
    public void testContext() {
        assertNotNull(accountRepository);
    }


    @Test
    @DisplayName("save and findById operation test")
    void saveAndFind() {
        CurrencyModel currencyNew = new CurrencyModel();
        currencyNew.setName("Dollar");
        AccountModel accountModel = new AccountModel();
        accountModel.setName("test");
        accountModel.setAccountType(AccountType.CASH);
        accountModel.setAmount(BigDecimal.valueOf(1));
        currencyRepository.save(currencyNew);
        accountModel.setCurrency(currencyRepository.findById((long) 1).orElse(null));
        accountRepository.save(accountModel);
        assertEquals(1, accountRepository.findById((long) 1).get().getId());
        assertEquals("test", accountRepository.findById((long) 1).get().getName());
    }
}
