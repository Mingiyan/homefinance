package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import ru.geekfactory.homefinace.dao.model.AccountModel;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceMock {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private ServiceCRUD accountServiceMock;


    @Test
    void testAccountService() {

    }
}
