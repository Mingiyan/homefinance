package ru.geekfactory.homefinance.dao;

import ru.geekfactory.homefinance.dao.model.*;
import ru.geekfactory.homefinance.dao.repository.AccountRepository;
import ru.geekfactory.homefinance.dao.repository.CategoryTransactionRepository;
import ru.geekfactory.homefinance.dao.repository.CurrencyRepository;
import ru.geekfactory.homefinance.dao.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;


public class Application {
    public static void main(String[] args) {
        TransactionRepository transactionRepository = new TransactionRepository();
        AccountRepository accountRepository = new AccountRepository();
        CategoryTransactionRepository categoryTransactionRepository = new CategoryTransactionRepository();
        CurrencyRepository currencyRepository = new CurrencyRepository();
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setName("euro");
        currencyRepository.save(currencyModel);
        AccountModel accountModel = new AccountModel();
        accountModel.setName("account");
        accountModel.setAmount(BigDecimal.valueOf(1));
        accountModel.setAccountType(AccountType.CASH);
        accountModel.setCurrency(currencyRepository.findById((long) 1).get());
        accountRepository.save(accountModel);
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setName("category");
        categoryTransactionRepository.save(categoryTransactionModel);
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setName("transaction");
        transactionModel.setAccount(accountRepository.findById((long) 1).orElse(null));
        transactionModel.setDateTime(LocalDateTime.now());
        Collection<CategoryTransactionModel> collection = new HashSet<>();
        collection.add(categoryTransactionRepository.findById((long) 1).orElse(null));
        transactionModel.setCategoryTransaction(collection);
        transactionRepository.save(transactionModel);
        System.out.println(categoryTransactionRepository.findById((long)1).get().getId());
    }
}
