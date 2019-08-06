package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinace.dao.model.CurrencyModel;
import ru.geekfactory.homefinace.dao.repository.CurrencyRepository;
import ru.geekfactory.homefinace.dao.repository.DatabaseConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceMock {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();

    @Mock
    private CurrencyRepository currencyRepositoryMock;

    @InjectMocks
    private CurrencyService currencyService;

    @Spy
    CurrencyService spy;

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
    }

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void testContext() {
        assertNotNull(currencyRepositoryMock);
    }

    @Test
    void testCurrencyService() {
        CurrencyModel currency = new CurrencyModel();
        currency.setId((long) 23);
        currency.setName("currency");
        when(currencyRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(currency));

        assertNotNull(currencyService);
        assertEquals((long) 23, currencyService.findById((long) 55).get().getId());
        assertEquals("currency", currencyService.findById((long) 654).get().getName());
        verify(currencyRepositoryMock, times(2)).findById(anyLong());
        verify(currencyRepositoryMock, never()).save(currency);
    }

    @Test
    void testServiceMock() {
        CurrencyModel first = new CurrencyModel();
        first.setId((long) 1);
        first.setName("first");
        CurrencyModel second = new CurrencyModel();
        second.setId((long) 2);
        second.setName("second");
        List<CurrencyModel> list = new ArrayList<>();

        when(currencyRepositoryMock.findAll()).thenReturn(list);
        assertNotNull(currencyRepositoryMock);
        assertEquals(list, currencyRepositoryMock.findAll());
    }

    @Test
    void testWithSpy() {
        CurrencyModel currency = new CurrencyModel();
        currency.setId((long) 1);
        currency.setName("test");

        when(spy.findById(anyLong())).thenReturn(Optional.ofNullable(currency));

        assertEquals("test", spy.findById((long) 2).get().getName());
        assertNotEquals((long) 44, spy.findById((long) 44).get().getId());
    }
}
