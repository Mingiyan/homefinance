package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.geekfactory.homefinance.dao.model.Currency;
import ru.geekfactory.homefinance.dao.repository.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceMockTest {

    @Mock
    private CurrencyRepository currencyRepositoryMock;

    @InjectMocks
    @Autowired
    private CurrencyService currencyService;

    @Test
    void testCurrencyService() {
        Currency currency = new Currency();
        currency.setCurrencyId(23L);
        currency.setName("currency");
        when(currencyRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(currency));

        Currency currencyFromData = currencyService.findById(55L).orElse(null);

        assertNotNull(currencyRepositoryMock);
        assertNotNull(currencyService);
        assertEquals(currency, currencyFromData);
        assertEquals("currency", currencyFromData.getName());
        verify(currencyRepositoryMock, times(1)).findById(anyLong());
        verify(currencyRepositoryMock, never()).save(currency);
    }

    @Test
    void testServiceMock() {
        Currency first = new Currency();
        first.setCurrencyId(1L);
        first.setName("first");
        Currency second = new Currency();
        second.setCurrencyId(2L);
        second.setName("second");
        List<Currency> list = new ArrayList<>();
        when(currencyRepositoryMock.findAll()).thenReturn(list);

        assertNotNull(currencyRepositoryMock);
        assertEquals(list, currencyRepositoryMock.findAll());
    }
}
