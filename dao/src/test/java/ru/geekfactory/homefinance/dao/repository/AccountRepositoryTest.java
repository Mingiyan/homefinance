package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.*;
import ru.geekfactory.homefinance.dao.model.AccountModel;
import ru.geekfactory.homefinance.dao.model.AccountType;
import ru.geekfactory.homefinance.dao.model.CurrencyModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();

    private AccountRepository accountRepository = new AccountRepository();

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
        databaseConnectorTest.initDB();
    }

    @BeforeEach
    void beforeEach() {
        databaseConnectorTest.clearTables();
    }

    @Test
    void testContext() {
        assertNotNull(accountRepository);
    }


    @Test
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        AccountModel accountModel = new AccountModel();
        accountModel.setId(1L);
        accountModel.setName("test");
        accountModel.setAccountType(AccountType.CASH);
        accountModel.setAmount(BigDecimal.valueOf(1));
        accountRepository.save(accountModel);
        AccountModel fromData = accountRepository.findById((long) 1).orElse(null);
        assertEquals(accountModel, fromData);
    }

    @Test
    @DisplayName("update operation test")
    void testUpdate() {
        AccountModel accountModel = new AccountModel();
        accountModel.setName("test");
        accountModel.setAccountType(AccountType.CASH);
        accountModel.setAmount(BigDecimal.valueOf(1));
        accountRepository.save(accountModel);
        AccountModel accountUpdate = accountRepository.findById((long) 1).orElse(null);
        accountUpdate.setName("test2");
        accountRepository.update(accountUpdate);
        assertNotEquals(accountModel, accountRepository.findById((long) 1));
    }

    @Test
    @DisplayName("findAll operation test")
    void testFindAll() {
        AccountModel first = new AccountModel();
        first.setName("first");
        first.setAccountType(AccountType.DEBIT_CARD);
        AccountModel second = new AccountModel();
        second.setName("second");
        second.setAmount(BigDecimal.valueOf(11));
        accountRepository.save(first);
        accountRepository.save(second);
        List<AccountModel> firstList = new ArrayList<>();
        List<AccountModel> list = accountRepository.findAll();
        firstList.add(first);
        firstList.add(second);
        assertNotNull(list);
        assertEquals(firstList, list);
    }

    @Test
    @DisplayName("remove operation test")
    void testRemove() {
        AccountModel firstAccount = new AccountModel();
        firstAccount.setName("first");
        firstAccount.setAmount(BigDecimal.valueOf(1));
        accountRepository.save(firstAccount);
        AccountModel secondAccount = accountRepository.findById((long) 1).orElse(null);
        accountRepository.remove(secondAccount);
        AccountModel removedAccount = accountRepository.findById((long) 1).orElse(null);
        assertNull(removedAccount);
    }
}
