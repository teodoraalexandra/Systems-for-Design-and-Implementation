package lab12.core.service;

import java.util.List;
import java.util.Optional;

public interface EntityService<T>
{
    Iterable<T> getAllEntities();

    List<T> getJPQL();

    T addEntity(T entity);

    void deleteEntity(Long id);

    T updateEntity(Long id, T updatedEntity);

    Optional<T> getEntity(Long id);
}
