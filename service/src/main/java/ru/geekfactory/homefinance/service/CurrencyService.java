package ru.geekfactory.homefinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekfactory.homefinance.dao.model.Currency;
import ru.geekfactory.homefinance.dao.repository.CurrencyRepository;
import ru.geekfactory.homefinance.model.ConvertedCurrency;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyService implements ServiceCRUD<Long, Currency> {

    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void save(Currency object) {
        currencyRepository.save(object);
    }

    @Override
    public Optional<Currency> findById(Long id) {
        return currencyRepository.findById(id);
    }

    @Override
    public Currency update(Currency object) {
        return currencyRepository.save(object);
    }

    @Override
    public void remove(Currency object) {
        currencyRepository.delete(object);
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public void removeConverted(ConvertedCurrency convertedCurrency) {
        Currency currency = new Currency();
        currency.setCurrencyId(convertedCurrency.getCurrencyId());
        remove(currency);
    }

    public void updateConverted(ConvertedCurrency object) {
        Currency currency = new Currency();
        currency.setCurrencyId(object.getCurrencyId());
        currency.setName(object.getName());
        update(currency);
    }

    public ConvertedCurrency findByIdConverted(Long id) {
        return findById(id)
                .map(currency -> new ConvertedCurrency(currency.getCurrencyId(), currency.getName()))
                .orElse(new ConvertedCurrency());
    }

    public void saveConverted(ConvertedCurrency object) {
        Currency currency = new Currency();
        currency.setName(object.getName());
        save(currency);
    }

    public List<ConvertedCurrency> findAllConverted() {
        return findAll().stream()
                .map(currency -> new ConvertedCurrency(currency.getCurrencyId(), currency.getName()))
                .collect(Collectors.toList());
    }
}
