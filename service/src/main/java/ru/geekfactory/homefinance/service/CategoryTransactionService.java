package ru.geekfactory.homefinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekfactory.homefinance.dao.model.CategoryTransaction;
import ru.geekfactory.homefinance.dao.repository.CategoryTransactionRepository;

import java.util.List;
import java.util.Optional;

@Service()
public class CategoryTransactionService implements ServiceCRUD<Long, CategoryTransaction> {

    @Autowired
    private CategoryTransactionRepository categoryTransactionRepository;

    @Override
    public void save(CategoryTransaction object) {
        categoryTransactionRepository.save(object);
    }

    @Override
    public Optional<CategoryTransaction> findById(Long id) {
        return categoryTransactionRepository.findById(id);
    }

    @Override
    public CategoryTransaction update(CategoryTransaction object) {
        return categoryTransactionRepository.save(object);
    }

    @Override
    public void remove(CategoryTransaction object) {
        categoryTransactionRepository.delete(object);
    }

    @Override
    public List<CategoryTransaction> findAll() {
        return categoryTransactionRepository.findAll();
    }
}
