package dordzhiev.service;

import java.util.List;
import java.util.Optional;

public interface ServiceCRUD<T, M> {
    void save(M object);
    Optional<M> findById(T id);
    M update(M object);
    void remove(M object);
    List<M> findAll();
}
