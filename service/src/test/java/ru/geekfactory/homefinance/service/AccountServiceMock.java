package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinace.dao.model.AccountModel;
import ru.geekfactory.homefinace.dao.repository.AccountRepository;
import ru.geekfactory.homefinace.dao.repository.DatabaseConnector;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceMock {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();

    @Mock
    private AccountRepository accountRepositoryMock;

    @Mock
    private AccountModel accountModel;

    @InjectMocks
    private AccountService accountService;

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
    }

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void testContext() {
        assertNotNull(accountRepositoryMock);
    }

    @Test
    void testAccountService() {
        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.of(accountModel));

        assertNotNull(accountService);
        assertEquals((long) 1, accountService.findById((long) 4).get().getId());
        assertNotEquals((long) 4, accountService.findById((long) 4).get().getId());

        verify(accountRepositoryMock, times(2)).findById(anyLong()).orElse(null);
        verify(accountRepositoryMock, never()).findAll();
    }
}
