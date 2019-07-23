package ru.dordzhiev;

import ru.dordzhiev.model.Account;
import ru.dordzhiev.model.AccountType;
import ru.dordzhiev.model.Currency;
import ru.dordzhiev.repository.AccountRepository;
import ru.dordzhiev.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.util.Optional;


public class Application {
    public static void main(String[] args) {
        Optional<Currency> currency;
        CurrencyRepository currencyRepository = new CurrencyRepository();
        currency = currencyRepository.findById((long) 1);

        Account account = new Account();
        account.setName("Хавчик");
        account.setAmount(BigDecimal.valueOf(1235));
        account.setAccountType(AccountType.CASH);
        account.setCurrency(currency);
        System.out.println(account);

        System.out.println(currencyRepository.findAll());
        AccountRepository accountRepository = new AccountRepository();

        accountRepository.save(account);
        System.out.println(accountRepository.findAll());
    }
}
