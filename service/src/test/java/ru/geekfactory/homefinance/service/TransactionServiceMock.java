package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinance.dao.model.TransactionModel;
import ru.geekfactory.homefinance.dao.repository.DatabaseConnector;
import ru.geekfactory.homefinance.dao.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceMock {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @InjectMocks
    private TransactionService transactionService;

    @Spy
    TransactionService spy;

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
    }

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void testContext() {
        assertNotNull(transactionRepositoryMock);
    }

    @Test
    void testTransactionService() {
        TransactionModel transaction = new TransactionModel();
        transaction.setName("transaction");
        transaction.setId((long) 1);
        transaction.setDateTime(LocalDateTime.now());

        when(transactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(transaction));

        assertNotNull(transactionService);
        assertNotEquals((long) 34, transactionService.findById((long) 34).get().getId());
        assertEquals("transaction", transactionService.findById((long) 44).get().getName());

        verify(transactionRepositoryMock, times(2)).findById(anyLong());
        verify(transactionRepositoryMock, never()).update(transaction);
        verify(transactionRepositoryMock, never()).findAll();
    }

    @Test
    void testServiceMock() {
        TransactionModel transaction = new TransactionModel();
        transaction.setId((long) 1);
        transaction.setName("test");
        transaction.setDateTime(LocalDateTime.of(2017, 4, 14, 9, 22));

        when(transactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(transaction));

        assertNotNull(transactionRepositoryMock);
        assertEquals("test", transactionRepositoryMock.findById((long) 4).get().getName());
        assertEquals(2017, transactionRepositoryMock.findById((long) 3).get().getDateTime().getYear());
    }

    @Test
    void testWithSpy() {
        TransactionModel first = new TransactionModel();
        first.setName("first");
        TransactionModel second = new TransactionModel();
        second.setName("second");
        List<TransactionModel> list = new ArrayList<>();
        list.add(first);
        list.add(second);

        when(spy.findAll()).thenReturn(list);
        assertEquals(list, spy.findAll());
    }
}
