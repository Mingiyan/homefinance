package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinance.dao.model.CategoryTransaction;
import ru.geekfactory.homefinance.dao.repository.CategoryTransactionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryTransactionServiceMockTest {

    @Mock
    private CategoryTransactionRepository categoryTransactionRepositoryMock;

    @InjectMocks
    private CategoryTransactionService categoryTransactionService;

    @Test
    void testCategoryTransactionService() {
        CategoryTransaction categoryTransaction = new CategoryTransaction();
        categoryTransaction.setCategoryId(2L);
        categoryTransaction.setName("test");
        when(categoryTransactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(categoryTransaction));

        CategoryTransaction categoryFromData = categoryTransactionService.findById(33L).orElse(null);

        assertNotNull(categoryTransactionService);
        assertEquals(categoryTransaction, categoryFromData);
        assertEquals("test", categoryFromData.getName());
        assertNotEquals( 3L, categoryFromData.getCategoryId());

        verify(categoryTransactionRepositoryMock, times(1)).findById(anyLong());
        verify(categoryTransactionRepositoryMock, never()).findAll();
        verify(categoryTransactionRepositoryMock, never()).delete(categoryTransaction);
    }

    @Test
    void testServiceMock() {
        CategoryTransaction categoryTransactionModel = new CategoryTransaction();
        categoryTransactionModel.setName("test");
        categoryTransactionModel.setCategoryId(1L);
        when(categoryTransactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(categoryTransactionModel));

        CategoryTransaction categoryFromData = categoryTransactionRepositoryMock.findById(33L).orElse(null);

        assertNotNull(categoryTransactionRepositoryMock);
        assertEquals(categoryTransactionModel, categoryFromData);
        assertEquals("test", categoryFromData.getName());
        assertNotEquals(3L, categoryFromData.getCategoryId());

        verify(categoryTransactionRepositoryMock, times(1)).findById(anyLong());
    }
}
