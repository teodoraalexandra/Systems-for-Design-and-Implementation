package repository_2.repository.sorting;

import repository_2.domain.BaseEntity;
import repository_2.repository.Repository;

import java.io.Serializable;

public interface SortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends Repository<ID, T> {

    Iterable<T> findAll(Sort sort);
}
