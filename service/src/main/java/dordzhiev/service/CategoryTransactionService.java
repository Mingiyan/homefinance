package dordzhiev.service;

import dordzhiev.model.CategoryTransaction;
import dordzhiev.repository.CategoryTransactionRepository;

import java.util.List;
import java.util.Optional;

public class CategoryTransactionService implements ServiceCRUD<Long, CategoryTransaction> {

    CategoryTransactionRepository categoryTransactionRepository = new CategoryTransactionRepository();
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
        return categoryTransactionRepository.update(object);
    }

    @Override
    public void remove(CategoryTransaction object) {
        categoryTransactionRepository.remove(object);
    }

    @Override
    public List<CategoryTransaction> findAll() {
        return categoryTransactionRepository.findAll();
    }
}
