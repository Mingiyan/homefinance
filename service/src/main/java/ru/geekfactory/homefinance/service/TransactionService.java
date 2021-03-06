package ru.geekfactory.homefinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekfactory.homefinance.dao.model.Transaction;
import ru.geekfactory.homefinance.dao.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ServiceCRUD<Long, Transaction> {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void save(Transaction object) {
        transactionRepository.save(object);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction update(Transaction object) {
        return transactionRepository.save(object);
    }

    @Override
    public void remove(Transaction object) {
        transactionRepository.delete(object);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}
