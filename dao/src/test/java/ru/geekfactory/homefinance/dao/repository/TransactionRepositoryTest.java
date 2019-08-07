package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.*;
import ru.geekfactory.homefinance.dao.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();
    private TransactionRepository transactionRepository = new TransactionRepository();
    private AccountRepository accountRepository = new AccountRepository();
    private CategoryTransactionRepository categoryTransactionRepository = new CategoryTransactionRepository();
    private CurrencyRepository currencyRepository = new CurrencyRepository();

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
        databaseConnectorTest.initDB();
    }

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void testContext() {
        assertNotNull(transactionRepository);
        assertNotNull(accountRepository);
        assertNotNull(categoryTransactionRepository);
        assertNotNull(currencyRepository);
    }

    @Test
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setName("euro");
        currencyRepository.save(currencyModel);
        AccountModel accountModel = new AccountModel();
        accountModel.setName("account");
        accountModel.setAmount(BigDecimal.valueOf(1));
        accountModel.setAccountType(AccountType.CASH);
        accountModel.setCurrency(currencyRepository.findById((long) 1).get());
        accountRepository.save(accountModel);
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setName("category");
        categoryTransactionRepository.save(categoryTransactionModel);
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setName("transaction");
        transactionModel.setAccount(accountRepository.findById((long) 1).orElse(null));
        transactionModel.setDateTime(LocalDateTime.now());
        Collection<CategoryTransactionModel> collection = new HashSet<>();
        collection.add(categoryTransactionRepository.findById((long) 1).orElse(null));
        transactionModel.setCategoryTransaction(collection);
        transactionRepository.save(transactionModel);
        assertEquals(1, categoryTransactionRepository.findById((long) 1).get().getId());
    }

    @Test
    @DisplayName("findAll operation test")
    void testFindAll() {
        List<TransactionModel> list = transactionRepository.findAll();
        list.forEach(transaction -> assertEquals(1, transaction.getId()));
    }

    @Test
    @DisplayName("update operation test")
    void testUpdate() {
        TransactionModel transactionModel = transactionRepository.findById((long) 1).get();
        transactionModel.setName("secondTransaction");
        transactionRepository.update(transactionModel);
        assertEquals("secondTransaction", transactionRepository.findById((long) 1).get().getName());
    }

    @Test
    @DisplayName("remove operation test")
    void testRemove() {
        TransactionModel transactionModel = transactionRepository.findById((long) 1).get();
        transactionRepository.remove(transactionModel);
        assertNull(categoryTransactionRepository.findById((long) 1));
    }
}
