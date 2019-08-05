package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.*;
import ru.geekfactory.homefinace.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinace.dao.repository.CategoryTransactionRepository;
import ru.geekfactory.homefinace.dao.repository.DatabaseConnector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryTransactionRepositoryTest {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();
    private CategoryTransactionRepository categoryTransactionRepository = new CategoryTransactionRepository();

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
        assertNotNull(categoryTransactionRepository);
    }

    @Test
    @Order(1)
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setName("test");
        categoryTransactionRepository.save(categoryTransactionModel);
        assertEquals(1, categoryTransactionRepository.findById((long) 1).get().getId());
    }

    @Test
    @Order(2)
    @DisplayName("findAll operation test")
    void testFindAll() {
        List<CategoryTransactionModel> list = categoryTransactionRepository.findAll();
        list.forEach(categoryTransaction -> assertEquals(1, categoryTransaction.getId()));
    }

    @Test
    @Order(3)
    @DisplayName("update operation test")
    void testUpdate() {
        CategoryTransactionModel parentCategory = categoryTransactionRepository.findById((long) 1).orElse(null);
        CategoryTransactionModel category = new CategoryTransactionModel();
        category.setName("second");
        category.setParentCategory(parentCategory);
        categoryTransactionRepository.save(category);
        CategoryTransactionModel updated = categoryTransactionRepository.findById((long) 2).get();
        updated.setName("test2");
        categoryTransactionRepository.update(updated);
        assertEquals("test2", categoryTransactionRepository.findById((long) 2).get().getName());
    }

    @Test
    @Order(4)
    @DisplayName("remove operation test")
    void testRemove() {
        CategoryTransactionModel categoryTransactionModel = categoryTransactionRepository.findById((long) 2).orElse(null);
        categoryTransactionRepository.remove(categoryTransactionModel);
        assertNull(categoryTransactionRepository.findById((long) 2).orElse(null));
    }
}
