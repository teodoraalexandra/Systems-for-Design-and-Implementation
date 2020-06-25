package lab12.core.service;

import lab12.core.model.Client;
import lab12.core.validators.ValidatorException;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public abstract class EntityServiceImpl<T> implements EntityService<T> {
    @PersistenceContext
    private EntityManager entityManager;

    private final JpaRepository<T, Long> repository;

    protected EntityServiceImpl(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> getEntity(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public List<T> getJPQL() {
        EntityManager entityManager = this.entityManager;
        Query query = entityManager.createQuery(
                "select distinct c from client c " +
                        "left join fetch c.rentals r " +
                        "left join fetch r.movie");
        List<T> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public T addEntity(T entity) {
        if (entity == null) throw new IllegalArgumentException();
        return this.repository.save(entity);
    }

    @Override
    public Iterable<T> getAllEntities() {
        return this.repository.findAll();
    }

    @Override
    public void deleteEntity(Long id) {
        Optional<T> oldEntity = repository.findById(id);
        repository.deleteById(id);
    }

    @Override
    @SneakyThrows
    public T updateEntity(Long id, T updatedEntity) {
        Optional<T> findOldEntity = repository.findById(id);
        if (findOldEntity.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        try {
            T update = repository.findById(id).orElse(updatedEntity);
            repository.delete(update);
            return repository.save(updatedEntity);

        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        }
    }
}
