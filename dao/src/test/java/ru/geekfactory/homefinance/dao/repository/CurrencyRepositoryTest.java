package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geekfactory.homefinance.dao.model.CurrencyModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyRepositoryTest {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();
    private CurrencyRepository currencyRepository = new CurrencyRepository();

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
        databaseConnectorTest.initDB();
    }

    @BeforeEach
    void beforeEach() {
        databaseConnectorTest.clearTables();
    }

    @Test
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setId(3L);
        currencyModel.setName("euro");
        currencyRepository.save(currencyModel);
        CurrencyModel currencyFromData = currencyRepository.findById(3L).orElse(null);

        assertEquals(currencyModel, currencyFromData);

    }

    @Test
    @DisplayName("findAll operation test")
    void testFindAll() {
        List<CurrencyModel> list = new ArrayList<>();
        CurrencyModel first = new CurrencyModel();
        first.setId(1L);
        first.setName("first");
        list.add(first);
        currencyRepository.save(first);
        CurrencyModel second = new CurrencyModel();
        second.setId(2L);
        second.setName("second");
        list.add(second);
        currencyRepository.save(second);
        List<CurrencyModel> listFromData = currencyRepository.findAll();

        assertEquals(list, listFromData);
    }

    @Test
    @DisplayName("update and findByName operation test")
    void testUpdate() {
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setName("test");
        currencyRepository.save(currencyModel);
        CurrencyModel updatedCurrency = currencyRepository.findByName("test").get();
        updatedCurrency.setName("test2");
        currencyRepository.update(updatedCurrency);
        CurrencyModel currencyFromData = currencyRepository.findByName("test2").orElse(null);

        assertEquals(updatedCurrency, currencyFromData);
    }

    @Test
    @DisplayName("remove operation test")
    void testRemove() {
        CurrencyModel currencyModel = currencyRepository.findById(1L).orElse(null);
        currencyRepository.remove(currencyModel);
        CurrencyModel removedCurrnecy = currencyRepository.findById(1L).orElse(null);

        assertNull(removedCurrnecy);
    }
}
