package dordzhiev.service;

import dordzhiev.model.Currency;
import dordzhiev.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

public class CurrencyService implements ServiceCRUD<Long, Currency> {

    CurrencyRepository currencyRepository = new CurrencyRepository();

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
        return currencyRepository.update(object);
    }

    @Override
    public void remove(Currency object) {
        currencyRepository.remove(object);
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }
}
