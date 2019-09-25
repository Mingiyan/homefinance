package ru.geekfactory.homefinance.dao.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.geekfactory.homefinance.dao.config.DaoConfig;
import ru.geekfactory.homefinance.dao.model.Account;
import ru.geekfactory.homefinance.dao.model.AccountType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class})
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("save and findById operation test")
    void testSaveAndFind() {
        Account account = new Account();
        account.setAccountId(3L);
        account.setName("test");
        account.setAccountType(AccountType.CASH);
        account.setAmount(BigDecimal.valueOf(11));
        accountRepository.save(account);
        Account fromData = accountRepository.findById(3L).orElse(null);

        assertEquals(account.getAccountId(), fromData.getAccountId());
    }

    @Test
    @DisplayName("update operation test")
    void testUpdate() {
        Account account = new Account();
        account.setAccountId(4L);
        account.setName("test");
        account.setAccountType(AccountType.CASH);
        account.setAmount(BigDecimal.valueOf(1));
        accountRepository.save(account);
        Account accountUpdate = accountRepository.findById(4L).orElse(null);
        accountUpdate.setName("test2");
        accountRepository.save(accountUpdate);

        assertNotEquals(accountUpdate, accountRepository.findById(4L));
    }

    @Test
    @DisplayName("findAll operation test")
    void testFindAll() {
        Account first = new Account();
        first.setAccountId(1L);
        first.setName("first");
        first.setAccountType(AccountType.DEBIT_CARD);
        Account second = new Account();
        second.setAccountId(2L);
        second.setName("second");
        second.setAmount(BigDecimal.ONE);
        second.setAccountType(AccountType.CASH);
        accountRepository.save(first);
        accountRepository.save(second);
        List<Account> firstList = new ArrayList<>();
        List<Account> list = accountRepository.findAll();
        firstList.add(first);
        firstList.add(second);

        assertNotNull(list);
        assertEquals(firstList.size(), list.size());
    }

    @Test
    @DisplayName("remove operation test")
    void testRemove() {
        Account firstAccount = new Account();
        firstAccount.setAccountId(1L);
        firstAccount.setName("first");
        firstAccount.setAccountType(AccountType.CREDIT_CARD);
        firstAccount.setAmount(BigDecimal.valueOf(1));
        accountRepository.save(firstAccount);
        Account secondAccount = accountRepository.findById(1L).orElse(null);
        accountRepository.delete(secondAccount);
        Account removedAccount = accountRepository.findById(1L).orElse(null);

        assertNull(removedAccount);
    }
}
