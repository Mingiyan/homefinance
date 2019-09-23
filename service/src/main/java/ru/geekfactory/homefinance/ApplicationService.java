package ru.geekfactory.homefinance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekfactory.homefinance.config.ServiceConfig;
import ru.geekfactory.homefinance.dao.model.Currency;
import ru.geekfactory.homefinance.service.CurrencyService;

import java.util.Collection;

public class ApplicationService {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);
        CurrencyService service = (CurrencyService) context.getBean("currencyService");
        Collection<Currency> currencies = service.findAll();
        for (Currency cu: currencies) {
            System.out.println(cu.getName());
        }
    }
}
