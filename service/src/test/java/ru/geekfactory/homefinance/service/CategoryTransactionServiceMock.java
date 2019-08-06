package ru.geekfactory.homefinance.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.geekfactory.homefinace.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinace.dao.repository.CategoryTransactionRepository;
import ru.geekfactory.homefinace.dao.repository.DatabaseConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class CategoryTransactionServiceMock {

    private static DatabaseConnector databaseConnectorTest = new DatabaseConnector();

    @Mock
    private CategoryTransactionRepository categoryTransactionRepositoryMock;

    @InjectMocks
    private CategoryTransactionService categoryTransactionService;

    @Spy
    CategoryTransactionService spy;

    @BeforeAll
    static void beforeAll() {
        databaseConnectorTest.getConnection();
    }

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void testContext() {
        assertNotNull(categoryTransactionRepositoryMock);
    }

    @Test
    void testCategoryTransactionService() {
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setId((long) 2);
        categoryTransactionModel.setName("test");
        when(categoryTransactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(categoryTransactionModel));

        assertNotNull(categoryTransactionService);
        assertEquals((long) 2, categoryTransactionService.findById((long) 33).get().getId());
        assertEquals("test", categoryTransactionService.findById((long) 44).get().getName());
        assertNotEquals((long) 3, categoryTransactionService.findById((long) 3).get().getId());

        verify(categoryTransactionRepositoryMock, times(3)).findById(anyLong());
        verify(categoryTransactionRepositoryMock, never()).findAll();
        verify(categoryTransactionRepositoryMock, never()).remove(categoryTransactionModel);
    }

    @Test
    void testService() {
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setName("test");
        categoryTransactionModel.setId((long) 1);
        when(categoryTransactionRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(categoryTransactionModel));

        assertNotNull(categoryTransactionRepositoryMock);
        assertEquals((long) 1, categoryTransactionRepositoryMock.findById((long) 33).get().getId());
        assertEquals("test", categoryTransactionRepositoryMock.findById((long) 44).get().getName());
        assertNotEquals((long) 3, categoryTransactionRepositoryMock.findById((long) 3).get().getId());

        verify(categoryTransactionRepositoryMock, times(3)).findById(anyLong());
    }

    @Test
    void testWithSpy() {
        CategoryTransactionModel firstCategory = new CategoryTransactionModel();
        firstCategory.setId((long) 1);
        firstCategory.setName("first");
        CategoryTransactionModel secondCategory = new CategoryTransactionModel();
        secondCategory.setId((long) 2);
        secondCategory.setName("second");

        List<CategoryTransactionModel> testList = new ArrayList<>();
        testList.add(firstCategory);
        testList.add(secondCategory);

        when(spy.findAll()).thenReturn(testList);

        assertEquals(testList, spy.findAll());
    }
}
