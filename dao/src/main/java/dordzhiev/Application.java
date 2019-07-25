package dordzhiev;

import dordzhiev.model.Account;
import dordzhiev.model.AccountType;
import dordzhiev.model.Currency;
import dordzhiev.repository.AccountRepository;
import dordzhiev.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.util.Optional;


public class Application {
    public static void main(String[] args) {
        Optional<Currency> currency;
        CurrencyRepository currencyRepository = new CurrencyRepository();
        currency = currencyRepository.findById((long) 2);

        Account account = new Account();
        account.setName("Одежда");
        account.setAmount(BigDecimal.valueOf(333));
        account.setAccountType(AccountType.CASH);
        account.setCurrency(currency.get());
        System.out.println(account);

        System.out.println(currencyRepository.findAll());
        AccountRepository accountRepository = new AccountRepository();

        accountRepository.save(account);
        System.out.println(accountRepository.findAll());
    }
}
