package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinance.dao.repository.CategoryTransactionRepository;
import ru.geekfactory.homefinance.dao.repository.DatabaseConnector;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryTransactionServiceMockTest {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();

    @Mock
    private CategoryTransactionRepository categoryTransactionRepositoryMock;

    @InjectMocks
    private CategoryTransactionService categoryTransactionService;

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
    }

    @Test
    void testCategoryTransactionService() {
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setId((long) 2);
        categoryTransactionModel.setName("test");
        when(categoryTransactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(categoryTransactionModel));

        CategoryTransactionModel categoryFromData = categoryTransactionService.findById(33L).orElse(null);

        assertNotNull(categoryTransactionService);
        assertEquals(categoryTransactionModel, categoryFromData);
        assertEquals("test", categoryFromData.getName());
        assertNotEquals((long) 3, categoryFromData.getId());

        verify(categoryTransactionRepositoryMock, times(1)).findById(anyLong());
        verify(categoryTransactionRepositoryMock, never()).findAll();
        verify(categoryTransactionRepositoryMock, never()).remove(categoryTransactionModel);
    }

    @Test
    void testServiceMock() {
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setName("test");
        categoryTransactionModel.setId(1L);
        when(categoryTransactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(categoryTransactionModel));

        CategoryTransactionModel categoryFromData = categoryTransactionRepositoryMock.findById(33L).orElse(null);

        assertNotNull(categoryTransactionRepositoryMock);
        assertEquals(categoryTransactionModel, categoryFromData);
        assertEquals("test", categoryFromData.getName());
        assertNotEquals(3L, categoryFromData.getId());

        verify(categoryTransactionRepositoryMock, times(1)).findById(anyLong());
    }
}
