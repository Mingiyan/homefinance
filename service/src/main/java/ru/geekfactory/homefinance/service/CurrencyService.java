package ru.geekfactory.homefinance.service;

import ru.geekfactory.homefinace.dao.model.CurrencyModel;
import ru.geekfactory.homefinace.dao.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

public class CurrencyService implements ServiceCRUD<Long, CurrencyModel> {

    private CurrencyRepository currencyRepository = new CurrencyRepository();

    @Override
    public void save(CurrencyModel object) {
        currencyRepository.save(object);
    }

    @Override
    public Optional<CurrencyModel> findById(Long id) {
        return currencyRepository.findById(id);
    }

    @Override
    public CurrencyModel update(CurrencyModel object) {
        return currencyRepository.update(object);
    }

    @Override
    public void remove(CurrencyModel object) {
        currencyRepository.remove(object);
    }

    @Override
    public List<CurrencyModel> findAll() {
        return currencyRepository.findAll();
    }
}
