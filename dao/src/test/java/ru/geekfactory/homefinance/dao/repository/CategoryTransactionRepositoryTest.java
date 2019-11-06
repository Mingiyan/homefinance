package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.geekfactory.homefinance.dao.config.DaoConfig;
import ru.geekfactory.homefinance.dao.model.CategoryTransaction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class})
class CategoryTransactionRepositoryTest {

    @Autowired
    private CategoryTransactionRepository categoryTransactionRepository;

    @Test
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        CategoryTransaction category = new CategoryTransaction();
        category.setCategoryId(13L);
        category.setName("test");
        categoryTransactionRepository.save(category);
        CategoryTransaction categoryFromData = categoryTransactionRepository.findById(13L).orElse(null);

        assertEquals(category.getCategoryId(), categoryFromData.getCategoryId());
    }

    @Test
    @DisplayName("findAll operation test")
    void testFindAll() {
        List<CategoryTransaction> list = new ArrayList<>();
        CategoryTransaction first = new CategoryTransaction();
        first.setCategoryId(1L);
        first.setName("first");
        CategoryTransaction second = new CategoryTransaction();
        second.setCategoryId(2L);
        second.setName("second");
        list.add(first);
        list.add(second);
        categoryTransactionRepository.save(first);
        categoryTransactionRepository.save(second);

        List<CategoryTransaction> listFromData = categoryTransactionRepository.findAll();

        assertEquals(list.size(), listFromData.size());
    }

    @Test
    @DisplayName("update operation test")
    void testUpdate() {
        CategoryTransaction category = new CategoryTransaction();
        category.setCategoryId(14L);
        category.setName("test");
        categoryTransactionRepository.save(category);
        CategoryTransaction updated = categoryTransactionRepository.findById(14L).orElse(null);
        updated.setName("test2");
        categoryTransactionRepository.save(updated);
        CategoryTransaction fromData = categoryTransactionRepository.findById(14L).orElse(null);

        assertEquals(updated.getCategoryId(), fromData.getCategoryId());
    }

    @Test
    @DisplayName("remove operation test")
    void testRemove() {
        CategoryTransaction category = new CategoryTransaction();
        category.setCategoryId(13L);
        category.setName("test");
        categoryTransactionRepository.save(category);
        CategoryTransaction categoryFromData = categoryTransactionRepository.findById(13L).orElse(null);
        categoryTransactionRepository.delete(categoryFromData);

        assertNull(categoryTransactionRepository.findById(13L).orElse(null));
    }
}
