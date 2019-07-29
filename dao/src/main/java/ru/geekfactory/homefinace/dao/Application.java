package ru.geekfactory.homefinace.dao;

import ru.geekfactory.homefinace.dao.model.Account;
import ru.geekfactory.homefinace.dao.model.AccountType;
import ru.geekfactory.homefinace.dao.model.Currency;
import ru.geekfactory.homefinace.dao.repository.AccountRepository;
import ru.geekfactory.homefinace.dao.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.util.Optional;


public class Application {
    public static void main(String[] args) {
        Currency currencyNew = new Currency();
        currencyNew.setName("Dollar");

        CurrencyRepository currencyRepository = new CurrencyRepository();
        currencyRepository.save(currencyNew);
        Optional<Currency> currency = currencyRepository.findById((long) 1);

        Account account = new Account();
        account.setName("Жратва");
        account.setAmount(BigDecimal.valueOf(100));
        account.setAccountType(AccountType.DEBIT_CARD);
        account.setCurrency(currency.get());
        System.out.println(account);

        System.out.println(currencyRepository.findAll());
        AccountRepository accountRepository = new AccountRepository();

        accountRepository.save(account);
        System.out.println(accountRepository.findById((long) 1));
    }
}
