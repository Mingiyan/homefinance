package dordzhiev.service;

import dordzhiev.model.Account;
import dordzhiev.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

public class AccountService implements ServiceCRUD<Long, Account> {

    AccountRepository accountRepository = new AccountRepository();

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
        return accountRepository.update(object);
    }

    @Override
    public void remove(Account object) {
        accountRepository.remove(object);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
