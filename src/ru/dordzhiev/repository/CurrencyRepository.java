package ru.dordzhiev.repository;

import ru.dordzhiev.model.Currency;

import java.util.List;
import java.util.Optional;

public class CurrencyRepository implements RepositoryCRUD<Long, Currency> {
    @Override
    public void save(Currency object) {

    }

    @Override
    public Optional<Currency> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Currency update(Currency object) {
        return null;
    }

    @Override
    public void remove(Currency object) {

    }

    @Override
    public List<Currency> findAll() {
        return null;
    }

    public Optional<Currency> findByName(String name) {
        return Optional.empty();
    }
}
