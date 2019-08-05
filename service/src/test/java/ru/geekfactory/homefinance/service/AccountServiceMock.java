package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinace.dao.model.AccountModel;
import ru.geekfactory.homefinace.dao.repository.AccountRepository;
import ru.geekfactory.homefinace.dao.repository.DatabaseConnector;

import java.math.BigDecimal;
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

    @InjectMocks
    private AccountService accountService;

    @Spy
    AccountService spy;

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
        AccountModel accountModel = new AccountModel();
        accountModel.setId((long) 22);
        accountModel.setName("test");
        accountModel.setAmount(BigDecimal.valueOf(1));
        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(accountModel));

        assertNotNull(accountService);
        assertEquals((long) 22, accountService.findById((long) 4).get().getId());
        assertEquals("test", accountService.findById((long) 20).get().getName());
        assertNotEquals((long) 4, accountService.findById((long) 4).get().getId());

        verify(accountRepositoryMock, times(3)).findById(anyLong());
        verify(accountRepositoryMock, never()).findAll();
        verify(accountRepositoryMock, never()).remove(accountModel);
        verify(accountRepositoryMock, never()).save(accountModel);
    }

    @Test
    void testService() {
        AccountModel accountModel = new AccountModel();
        accountModel.setId((long) 1);
        accountModel.setName("test");
        accountModel.setAmount(BigDecimal.valueOf(1));
        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(accountModel));

        assertNotNull(accountRepositoryMock);
        assertEquals((long) 1, accountRepositoryMock.findById((long) 4).get().getId());
        assertEquals("test", accountRepositoryMock.findById((long) 20).get().getName());
        assertNotEquals((long) 4, accountRepositoryMock.findById((long) 4).get().getId());

        verify(accountRepositoryMock, times(3)).findById(anyLong());
    }

    @Test
    void spyObjectTest() {
        AccountModel accountModel = new AccountModel();
        accountModel.setId((long) 1);
        accountModel.setName("test");
        accountModel.setAmount(BigDecimal.valueOf(1));
        when(spy.findById(anyLong())).thenReturn(Optional.ofNullable(accountModel));

        assertEquals(1, spy.findById((long) 66).get().getId());
        assertEquals("test", spy.findById((long) 20).get().getName());
    }
}
