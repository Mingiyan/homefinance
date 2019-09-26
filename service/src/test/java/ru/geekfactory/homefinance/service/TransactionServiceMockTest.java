package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinance.dao.model.Transaction;
import ru.geekfactory.homefinance.dao.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceMockTest {

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testTransactionService() {
        Transaction transaction = new Transaction();
        transaction.setName("transaction");
        transaction.setTransactionId(1L);
        transaction.setDateTime(LocalDateTime.now());

        when(transactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(transaction));

        Transaction transactionFromData = transactionService.findById(34L).orElse(null);

        assertNotNull(transactionRepositoryMock);
        assertNotNull(transactionService);
        assertEquals(transaction, transactionFromData);
        assertEquals("transaction", transactionFromData.getName());

        verify(transactionRepositoryMock, times(1)).findById(anyLong());
        verify(transactionRepositoryMock, never()).save(transaction);
        verify(transactionRepositoryMock, never()).findAll();
    }

    @Test
    void testServiceMock() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);
        transaction.setName("test");
        transaction.setDateTime(LocalDateTime.of(2017, 4, 14, 9, 22));

        when(transactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(transaction));

        Transaction transactionFromData = transactionRepositoryMock.findById(4L).orElse(null);

        assertNotNull(transactionRepositoryMock);
        assertEquals(transaction,transactionFromData);
        assertEquals(2017, transactionFromData.getDateTime().getYear());

        verify(transactionRepositoryMock, times(1)).findById(anyLong());
    }
}
