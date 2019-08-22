package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinance.dao.model.CurrencyModel;
import ru.geekfactory.homefinance.dao.repository.CurrencyRepository;
import ru.geekfactory.homefinance.dao.repository.DatabaseConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceMockTest {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();

    @Mock
    private CurrencyRepository currencyRepositoryMock;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
    }

    @Test
    void testCurrencyService() {
        CurrencyModel currency = new CurrencyModel();
        currency.setId((long) 23);
        currency.setName("currency");
        when(currencyRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(currency));
        CurrencyModel currencyFromData = currencyService.findById(55L).orElse(null);

        assertNotNull(currencyRepositoryMock);
        assertNotNull(currencyService);
        assertEquals(currency, currencyFromData);
        assertEquals("currency", currencyFromData.getName());
        verify(currencyRepositoryMock, times(1)).findById(anyLong());
        verify(currencyRepositoryMock, never()).save(currency);
    }

    @Test
    void testServiceMock() {
        CurrencyModel first = new CurrencyModel();
        first.setId(1L);
        first.setName("first");
        CurrencyModel second = new CurrencyModel();
        second.setId(2L);
        second.setName("second");
        List<CurrencyModel> list = new ArrayList<>();
        when(currencyRepositoryMock.findAll()).thenReturn(list);

        assertNotNull(currencyRepositoryMock);
        assertEquals(list, currencyRepositoryMock.findAll());
    }
}
