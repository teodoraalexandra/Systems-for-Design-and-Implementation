package lab12.core.repository;

import lab12.core.model.Client;

import java.util.List;

public interface ClientRepositoryCustom {
    List<Client> findAllJPQL();
}
