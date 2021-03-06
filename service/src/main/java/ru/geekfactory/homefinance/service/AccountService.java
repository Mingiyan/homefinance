package ru.geekfactory.homefinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekfactory.homefinance.dao.model.Account;
import ru.geekfactory.homefinance.dao.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements ServiceCRUD<Long, Account> {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void save(Account object) {
        accountRepository.save(object);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account update(Account object) {
        return accountRepository.save(object);
    }

    @Override
    public void remove(Account object) {
        accountRepository.delete(object);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
