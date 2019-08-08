package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        databaseConnectorTest.clearTables();
    }

    @Test
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setId(3L);
        categoryTransactionModel.setName("test");
        categoryTransactionRepository.save(categoryTransactionModel);
        CategoryTransactionModel categoryFromData = categoryTransactionRepository.findById(3L).orElse(null);

        assertEquals(categoryTransactionModel, categoryFromData);
    }

    @Test
    @DisplayName("findAll operation test")
    void testFindAll() {
        List<CategoryTransactionModel> list = new ArrayList<>();
        CategoryTransactionModel first = new CategoryTransactionModel();
        first.setId(1L);
        first.setName("first");
        CategoryTransactionModel second = new CategoryTransactionModel();
        second.setId(2L);
        second.setName("second");
        list.add(first);
        list.add(second);
        categoryTransactionRepository.save(first);
        categoryTransactionRepository.save(second);

        List<CategoryTransactionModel> listFromData = categoryTransactionRepository.findAll();

        assertEquals(list, listFromData);
    }

    @Test
    @DisplayName("update operation test")
    void testUpdate() {
        CategoryTransactionModel category = new CategoryTransactionModel();
        category.setId(5L);
        category.setName("test");
        categoryTransactionRepository.save(category);
        CategoryTransactionModel updated = categoryTransactionRepository.findById(5L).orElse(null);
        updated.setName("test2");
        categoryTransactionRepository.update(updated);
        CategoryTransactionModel fromData = categoryTransactionRepository.findById(5L).orElse(null);

        assertEquals(updated, fromData);
    }

    @Test
    @DisplayName("remove operation test")
    void testRemove() {
        CategoryTransactionModel category = new CategoryTransactionModel();
        category.setId(4L);
        category.setName("test");
        categoryTransactionRepository.save(category);
        CategoryTransactionModel categoryFromData = categoryTransactionRepository.findById(4L).orElse(null);
        categoryTransactionRepository.remove(categoryFromData);

        assertNull(categoryTransactionRepository.findById(4L).orElse(null));
    }
}
