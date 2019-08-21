package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.*;
import ru.geekfactory.homefinance.dao.model.AccountModel;
import ru.geekfactory.homefinance.dao.model.AccountType;

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
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        AccountModel accountModel = new AccountModel();
        accountModel.setId(3L);
        accountModel.setName("test");
        accountModel.setAccountType(AccountType.CASH);
        accountModel.setAmount(BigDecimal.valueOf(1));
        accountRepository.save(accountModel);
        AccountModel fromData = accountRepository.findById(3L).orElse(null);

        assertEquals(accountModel, fromData);
    }

    @Test
    @DisplayName("update operation test")
    void testUpdate() {
        AccountModel accountModel = new AccountModel();
        accountModel.setId(4L);
        accountModel.setName("test");
        accountModel.setAccountType(AccountType.CASH);
        accountModel.setAmount(BigDecimal.valueOf(1));
        accountRepository.save(accountModel);
        AccountModel accountUpdate = accountRepository.findById(4L).orElse(null);
        accountUpdate.setName("test2");
        accountRepository.update(accountUpdate);

        assertNotEquals(accountUpdate, accountRepository.findById(4L));
    }

    @Test
    @DisplayName("findAll operation test")
    void testFindAll() {
        AccountModel first = new AccountModel();
        first.setId(1L);
        first.setName("first");
        first.setAccountType(AccountType.DEBIT_CARD);
        AccountModel second = new AccountModel();
        second.setId(2L);
        second.setName("second");
        second.setAmount(BigDecimal.valueOf(11));
        second.setAccountType(AccountType.CASH);
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
        firstAccount.setId(1L);
        firstAccount.setName("first");
        firstAccount.setAccountType(AccountType.CREDIT_CARD);
        firstAccount.setAmount(BigDecimal.valueOf(1));
        accountRepository.save(firstAccount);
        AccountModel secondAccount = accountRepository.findById(1L).orElse(null);
        accountRepository.remove(secondAccount);
        AccountModel removedAccount = accountRepository.findById(1L).orElse(null);

        assertNull(removedAccount);
    }
}
