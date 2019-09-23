package ru.geekfactory.homefinance.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekfactory.homefinance.dao.config.DaoConfig;
import ru.geekfactory.homefinance.dao.model.Currency;
import ru.geekfactory.homefinance.dao.repository.CurrencyRepository;

import java.util.Collection;

public class ApplicationDao {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoConfig.class);
        CurrencyRepository repository = (CurrencyRepository) context.getBean("currencyRepository");
        Collection<Currency> currencies = repository.findAll();
        for (Currency cu: currencies) {
            System.out.println(cu.getName());
        }
    }
}
