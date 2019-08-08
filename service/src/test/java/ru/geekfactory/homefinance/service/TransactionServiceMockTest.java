package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinance.dao.model.TransactionModel;
import ru.geekfactory.homefinance.dao.repository.DatabaseConnector;
import ru.geekfactory.homefinance.dao.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceMockTest {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
    }

    @Test
    void testTransactionService() {
        TransactionModel transaction = new TransactionModel();
        transaction.setName("transaction");
        transaction.setId(1L);
        transaction.setDateTime(LocalDateTime.now());

        when(transactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(transaction));
        TransactionModel transactionFromData = transactionService.findById(34L).orElse(null);

        assertNotNull(transactionRepositoryMock);
        assertNotNull(transactionService);
        assertEquals(transaction, transactionFromData);
        assertEquals("transaction", transactionFromData.getName());

        verify(transactionRepositoryMock, times(1)).findById(anyLong());
        verify(transactionRepositoryMock, never()).update(transaction);
        verify(transactionRepositoryMock, never()).findAll();
    }

    @Test
    void testServiceMock() {
        TransactionModel transaction = new TransactionModel();
        transaction.setId(1L);
        transaction.setName("test");
        transaction.setDateTime(LocalDateTime.of(2017, 4, 14, 9, 22));

        when(transactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(transaction));
        TransactionModel transactionFromData = transactionRepositoryMock.findById(4L).orElse(null);

        assertNotNull(transactionRepositoryMock);
        assertEquals(transaction,transactionFromData);
        assertEquals(2017, transactionFromData.getDateTime().getYear());

        verify(transactionRepositoryMock, times(1)).findById(anyLong());
    }
}
