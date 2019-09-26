package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.geekfactory.homefinance.dao.config.DaoConfig;
import ru.geekfactory.homefinance.dao.model.Currency;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class})
class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        Currency currency = new Currency();
        currency.setCurrencyId(9L);
        currency.setName("euro");
        currencyRepository.save(currency);
        Currency currencyFromData = currencyRepository.findById(9L).orElse(null);

        assertEquals(currency.getName(), currencyFromData.getName());

    }

    @Test
    @DisplayName("findAll operation test")
    void testFindAll() {
        List<Currency> list = new ArrayList<>();
        Currency first = new Currency();
        first.setCurrencyId(1L);
        first.setName("first");
        list.add(first);
        currencyRepository.save(first);
        Currency second = new Currency();
        second.setCurrencyId(2L);
        second.setName("second");
        list.add(second);
        currencyRepository.save(second);
        List<Currency> listFromData = currencyRepository.findAll();

        assertEquals(list.size(), listFromData.size());
    }

    @Test
    @DisplayName("update and findById operation test")
    void testUpdate() {
        Currency currency = new Currency();
        currency.setName("test");
        currencyRepository.save(currency);
        Currency updatedCurrency = currencyRepository.findById(10L).orElse(null);
        updatedCurrency.setName("test2");
        currencyRepository.save(updatedCurrency);
        Currency currencyFromData = currencyRepository.findById(10L).orElse(null);
        assertEquals(updatedCurrency.getCurrencyId(), currencyFromData.getCurrencyId());
    }

    @Test
    @DisplayName("remove operation test")
    void testRemove() {
        Currency currency = currencyRepository.findById(8L).orElse(null);
        currencyRepository.delete(currency);
        Currency removedCurrnecy = currencyRepository.findById(8L).orElse(null);

        assertNull(removedCurrnecy);
    }
}
