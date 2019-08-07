package ru.geekfactory.homefinance.service;

import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinance.dao.repository.CategoryTransactionRepository;

import java.util.List;
import java.util.Optional;

public class CategoryTransactionService implements ServiceCRUD<Long, CategoryTransactionModel> {

    private CategoryTransactionRepository categoryTransactionRepository = new CategoryTransactionRepository();
    @Override
    public void save(CategoryTransactionModel object) {
        categoryTransactionRepository.save(object);
    }

    @Override
    public Optional<CategoryTransactionModel> findById(Long id) {
        return categoryTransactionRepository.findById(id);
    }

    @Override
    public CategoryTransactionModel update(CategoryTransactionModel object) {
        return categoryTransactionRepository.update(object);
    }

    @Override
    public void remove(CategoryTransactionModel object) {
        categoryTransactionRepository.remove(object);
    }

    @Override
    public List<CategoryTransactionModel> findAll() {
        return categoryTransactionRepository.findAll();
    }
}
