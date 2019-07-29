package ru.geekfactory.homefinance.service;

import ru.geekfactory.homefinace.dao.model.AccountModel;
import ru.geekfactory.homefinace.dao.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

public class AccountService implements ServiceCRUD<Long, AccountModel> {

    AccountRepository accountRepository = new AccountRepository();

    @Override
    public void save(AccountModel object) {
        accountRepository.save(object);
    }

    @Override
    public Optional<AccountModel> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public AccountModel update(AccountModel object) {
        return accountRepository.update(object);
    }

    @Override
    public void remove(AccountModel object) {
        accountRepository.remove(object);
    }

    @Override
    public List<AccountModel> findAll() {
        return accountRepository.findAll();
    }
}
