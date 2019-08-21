package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.*;
import ru.geekfactory.homefinance.dao.model.TransactionModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransactionRepositoryTest {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();
    private TransactionRepository transactionRepository = new TransactionRepository();

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
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setName("transaction");
        transactionModel.setDateTime(LocalDateTime.now());
        transactionRepository.save(transactionModel);

        assertEquals(transactionModel, transactionRepository.findById(1L).orElse(null));
    }

    @Test
    @DisplayName("findAll operation test")
    void testFindAll() {
        List<TransactionModel> list = new ArrayList<>();

        TransactionModel first = new TransactionModel();
        first.setId(1L);
        first.setName("first");
        transactionRepository.save(first);
        TransactionModel second = new TransactionModel();
        second.setId(2L);
        second.setName("second");
        transactionRepository.save(second);

        list.add(first);
        list.add(second);

        List<TransactionModel> listFromData = transactionRepository.findAll();

        assertEquals(list, listFromData);
    }

    @Test
    @DisplayName("update operation test")
    void testUpdate() {
        TransactionModel transaction = new TransactionModel();
        transaction.setId(1L);
        transaction.setName("test");
        transactionRepository.save(transaction);
        TransactionModel transactionModel = transactionRepository.findById(1L).orElse(null);
        transactionModel.setName("test2");
        transactionRepository.update(transactionModel);

        assertEquals(transaction, transactionRepository.findById(1L).orElse(null));
    }

    @Test
    @DisplayName("remove operation test")
    void testRemove() {
        TransactionModel transaction = new TransactionModel();
        transaction.setId(1L);
        transaction.setName("toRemove");
        TransactionModel transactionModel = transactionRepository.findById(1L).orElse(null);
        transactionRepository.remove(transactionModel);

        assertNull(transactionRepository.findById(1L));
    }
}
