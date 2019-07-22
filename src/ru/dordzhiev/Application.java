package ru.dordzhiev;

import ru.dordzhiev.model.Currency;
import ru.dordzhiev.repository.CurrencyRepository;


public class Application {
    public static void main(String[] args) {
        Currency currency = new Currency();
        currency.setName("Доллар");
        System.out.println(currency);

        CurrencyRepository currencyRepository = new CurrencyRepository();
//        currencyRepository.save(currency);
        System.out.println(currencyRepository.findAll());
        System.out.println(currencyRepository.findById((long)2));
        System.out.println(currencyRepository.findAll());
        currency.setName("Евро");
        currency.setId((long)2);
        currencyRepository.update(currency);
        currencyRepository.remove(currency);
    }
}
