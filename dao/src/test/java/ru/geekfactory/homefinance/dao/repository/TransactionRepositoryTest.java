package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.geekfactory.homefinance.dao.config.DaoConfig;
import ru.geekfactory.homefinance.dao.model.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class})
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(12L);
        transaction.setName("transaction");
        transaction.setDateTime(LocalDateTime.now());
        transactionRepository.save(transaction);
        Transaction fromData = transactionRepository.findById(12L).orElse(null);

        assertEquals(transaction.getTransactionId(), fromData.getTransactionId());
    }

    @Test
    @DisplayName("findAll operation test")
    void testFindAll() {
        List<Transaction> list = new ArrayList<>();
        Transaction first = new Transaction();
        first.setTransactionId(1L);
        first.setName("first");
        Transaction second = new Transaction();
        second.setTransactionId(2L);
        second.setName("second");
        list.add(first);
        list.add(second);
        transactionRepository.save(first);
        transactionRepository.save(second);
        List<Transaction> listFromData = transactionRepository.findAll();

        assertEquals(list.size(), listFromData.size());
    }

    @Test
    @DisplayName("update operation test")
    void testUpdate() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(13L);
        transaction.setName("secondTransaction");
        transactionRepository.save(transaction);
        Transaction fromData = transactionRepository.findById(13L).orElse(null);

        assertEquals(transaction.getTransactionId(), fromData.getTransactionId());
    }

    @Test
    @DisplayName("remove operation test")
    void testRemove() {
        Transaction first = new Transaction();
        first.setTransactionId(11L);
        first.setName("test");
        transactionRepository.save(first);
        Transaction transaction = transactionRepository.findById(11L).orElse(null);
        transactionRepository.delete(transaction);

        assertNull(transactionRepository.findById(11L).orElse(null));
    }
}
