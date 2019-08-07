package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.*;
import ru.geekfactory.homefinance.dao.model.AccountModel;
import ru.geekfactory.homefinance.dao.model.AccountType;
import ru.geekfactory.homefinance.dao.model.CurrencyModel;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountRepositoryTest {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();

    private AccountRepository accountRepository = new AccountRepository();
    private CurrencyRepository currencyRepository = new CurrencyRepository();


    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
        databaseConnectorTest.initDB();
    }

    @BeforeEach
    void beforeEach() {
        // удаление данных из база делать чтобы не было зависимости между тестами
    }

    @Test
    void testContext() {
        assertNotNull(accountRepository);
    }


    @Test
    @Order(1)
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        CurrencyModel currencyNew = new CurrencyModel(); // можно убрать Currency
        currencyNew.setName("Dollar");
        AccountModel accountModel = new AccountModel();
        accountModel.setName("test");
        accountModel.setAccountType(AccountType.CASH);
        accountModel.setAmount(BigDecimal.valueOf(1));
        currencyRepository.save(currencyNew);
        accountModel.setCurrency(currencyRepository.findById((long) 1).orElse(null));
        accountRepository.save(accountModel);
        assertEquals(1, accountRepository.findById((long) 1).get().getId()); // проверять модели
    }

    @Test
    @Order(3)
    @DisplayName("update operation test")
    void testUpdate() {
        CurrencyModel currencyNew = new CurrencyModel();
        currencyNew.setName("Dollar");
        AccountModel accountModel = new AccountModel();
        accountModel.setName("test");
        accountModel.setAccountType(AccountType.CASH);
        accountModel.setAmount(BigDecimal.valueOf(1));
        currencyRepository.save(currencyNew);
        accountModel.setCurrency(currencyRepository.findById((long) 1).orElse(null));
        accountRepository.save(accountModel);
        assertNotNull(accountModel);
        AccountModel accountUpdate = accountRepository.findById((long) 1).orElse(null);
        accountUpdate.setName("test2");
        accountRepository.update(accountUpdate);
        assertEquals("test2", accountRepository.findById((long) 1).get().getName());
    }

    @Test
    @Order(2)
    @DisplayName("findAll operation test")
    void testFindAll() {
        List<AccountModel> list = accountRepository.findAll();
        //assertNotNull
        list.forEach(accountModel -> assertEquals(1, accountModel.getId())); // модели сравнивать
    }

    @Test
    @Order(4)
    @DisplayName("remove operation test")
    void testRemove() {
        AccountModel accountModel = accountRepository.findById((long) 1).orElse(null);
        accountRepository.remove(accountModel);
        AccountModel removedAccount = accountRepository.findById((long) 1).orElse(null);
        assertNull(removedAccount);
    }
}
