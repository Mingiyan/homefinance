package dordzhiev.repository;

import java.util.List;
import java.util.Optional;

public interface RepositoryCRUD<T, M> {
    void save(M object);
    Optional<M> findById(T id);
    M update(M object);
    void remove(M object);
    List<M> findAll();
}
