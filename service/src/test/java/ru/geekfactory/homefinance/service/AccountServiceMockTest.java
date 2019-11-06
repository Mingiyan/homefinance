package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinance.dao.model.Account;
import ru.geekfactory.homefinance.dao.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceMockTest {

    @Mock
    private AccountRepository accountRepositoryMock;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testAccountService() {
        Account accountModel = new Account();
        accountModel.setAccountId(22L);
        accountModel.setName("test");
        accountModel.setAmount(BigDecimal.valueOf(1));
        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(accountModel));

        Account accountFromData = accountService.findById(4L).orElse(null);

        assertNotNull(accountRepositoryMock);
        assertNotNull(accountService);
        assertEquals(accountModel, accountFromData);
        assertEquals("test", accountFromData.getName());
        assertNotEquals(4L, accountFromData.getAccountId());

        verify(accountRepositoryMock, times(1)).findById(anyLong());
        verify(accountRepositoryMock, never()).findAll();
        verify(accountRepositoryMock, never()).delete(accountModel);
        verify(accountRepositoryMock, never()).save(accountModel);
    }

    @Test
    void testServiceMock() {
        Account accountModel = new Account();
        accountModel.setAccountId(1L);
        accountModel.setName("test");
        accountModel.setAmount(BigDecimal.valueOf(1));
        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(accountModel));

        Account accountFromData = accountRepositoryMock.findById(4L).orElse(null);

        assertNotNull(accountRepositoryMock);
        assertEquals(accountModel, accountFromData);
        assertEquals("test", accountFromData.getName());
        assertNotEquals(4L, accountFromData.getAccountId());

        verify(accountRepositoryMock, times(1)).findById(anyLong());
    }
}
