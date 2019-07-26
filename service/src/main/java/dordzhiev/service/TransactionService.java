package dordzhiev.service;

import dordzhiev.model.Transaction;
import dordzhiev.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

public class TransactionService  implements ServiceCRUD<Long, Transaction> {

    TransactionRepository transactionRepository = new TransactionRepository();

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
        return transactionRepository.update(object);
    }

    @Override
    public void remove(Transaction object) {
        transactionRepository.remove(object);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}
