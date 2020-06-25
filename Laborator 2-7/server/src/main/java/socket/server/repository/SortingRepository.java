package socket.server.repository;

import socket.common.domain.BaseEntity;
import socket.server.repository.Repository;

import java.io.Serializable;

public interface SortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends Repository<ID, T> {

    Iterable<T> findAll(Sort sort);
}
