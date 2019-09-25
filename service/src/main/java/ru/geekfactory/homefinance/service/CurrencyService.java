package ru.geekfactory.homefinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekfactory.homefinance.dao.model.Currency;
import ru.geekfactory.homefinance.dao.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService implements ServiceCRUD<Long, Currency> {

    @Autowired
    private CurrencyRepository currencyRepository;

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
}
