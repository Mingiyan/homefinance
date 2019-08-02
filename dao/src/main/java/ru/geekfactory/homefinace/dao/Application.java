package ru.geekfactory.homefinace.dao;

import ru.geekfactory.homefinace.dao.model.*;
import ru.geekfactory.homefinace.dao.repository.AccountRepository;
import ru.geekfactory.homefinace.dao.repository.CategoryTransactionRepository;
import ru.geekfactory.homefinace.dao.repository.CurrencyRepository;
import ru.geekfactory.homefinace.dao.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;


public class Application {
    public static void main(String[] args) {
        CurrencyModel currencyNew = new CurrencyModel();
        currencyNew.setName("Dollar");

        CurrencyRepository currencyRepository = new CurrencyRepository();
//        currencyRepository.save(currencyNew);
        Optional<CurrencyModel> currency = currencyRepository.findById((long) 1);

//        AccountModel account = new AccountModel();
//        account.setName("Жратва");
//        account.setAmount(BigDecimal.valueOf(100));
//        account.setAccountType(AccountType.DEBIT_CARD);
//        account.setCurrency(currency.get());
//        System.out.println(account);

        System.out.println(currencyRepository.findAll());
        AccountRepository accountRepository = new AccountRepository();

//        accountRepository.save(account);
        System.out.println(accountRepository.findById((long) 1));

//        AccountRepository accountRepository = new AccountRepository();

//
        TransactionModel transactionModel = new TransactionModel();
//        transactionModel.setName("Testo bortcok");
        AccountModel account = accountRepository.findById((long) 1).orElse(null);
//        transactionModel.setAccount(accountModel.orElse(null));
//        transactionModel.setDateTime(LocalDateTime.now());
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setName("1 category");
        CategoryTransactionRepository categoryTransactionRepository = new CategoryTransactionRepository();
        categoryTransactionRepository.save(categoryTransactionModel);
        Collection<CategoryTransactionModel> collection = categoryTransactionRepository.findAll();
//        System.out.println(collection);
//        transactionModel.setCategoryTransaction(collection);
        TransactionRepository transactionRepository = new TransactionRepository();
//        System.out.println(transactionModel);
//        transactionRepository.update(transactionModel);

//        TransactionModel transactionModel = transactionRepository.findById((long) 21).orElse(null);
//        transactionRepository.remove(transactionModel);
        transactionModel.setName("getter setter");
        transactionModel.setAccount(account);
        transactionModel.setCategoryTransaction(collection);
        transactionRepository.save(transactionModel);
        System.out.println(transactionRepository.findAll());

    }
}
