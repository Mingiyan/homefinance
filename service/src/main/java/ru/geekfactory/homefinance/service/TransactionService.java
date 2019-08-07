package ru.geekfactory.homefinance.service;

import ru.geekfactory.homefinance.dao.model.TransactionModel;
import ru.geekfactory.homefinance.dao.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

public class TransactionService implements ServiceCRUD<Long, TransactionModel> {

    private TransactionRepository transactionRepository = new TransactionRepository();

    @Override
    public void save(TransactionModel object) {
        transactionRepository.save(object);
    }

    @Override
    public Optional<TransactionModel> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public TransactionModel update(TransactionModel object) {
        return transactionRepository.update(object);
    }

    @Override
    public void remove(TransactionModel object) {
        transactionRepository.remove(object);
    }

    @Override
    public List<TransactionModel> findAll() {
        return transactionRepository.findAll();
    }
}
