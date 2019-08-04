package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.*;
import ru.geekfactory.homefinace.dao.model.CurrencyModel;
import ru.geekfactory.homefinace.dao.repository.CurrencyRepository;
import ru.geekfactory.homefinace.dao.repository.DatabaseConnector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CurrencyRepositoryTest {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();
    private CurrencyRepository currencyRepository = new CurrencyRepository();

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
        databaseConnectorTest.initDB();
    }

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void testContext() {
        assertNotNull(currencyRepository);
    }

    @Test
    @Order(1)
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setName("euro");
        currencyRepository.save(currencyModel);
        assertEquals("euro", currencyRepository.findById((long) 1).get().getName());
    }

    @Test
    @Order(2)
    @DisplayName("findAll operation test")
    void testFindAll() {
        List<CurrencyModel> list = currencyRepository.findAll();
        list.forEach(currencyModel -> assertEquals(1, currencyModel.getId()));
    }

    @Test
    @Order(3)
    @DisplayName("update and findByName operation test")
    void testUpdate() {
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setName("test");
        currencyRepository.save(currencyModel);
        CurrencyModel updatedCurrency = currencyRepository.findByName("test").get();
        updatedCurrency.setName("test2");
        currencyRepository.update(updatedCurrency);
        assertEquals("test2", currencyRepository.findByName("test2").get().getName());
    }

    @Test
    @Order(4)
    @DisplayName("remove operation test")
    void testRemove() {
        CurrencyModel currencyModel = currencyRepository.findById((long) 1).orElse(null);
        currencyRepository.remove(currencyModel);
        CurrencyModel removedCurrnecy = currencyRepository.findById((long) 1).orElse(null);
        assertNull(removedCurrnecy);
    }
}
