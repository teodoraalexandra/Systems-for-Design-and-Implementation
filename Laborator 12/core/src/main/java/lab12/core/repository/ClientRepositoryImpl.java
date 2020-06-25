package lab12.core.repository;

import lab12.core.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ClientRepositoryImpl extends CustomRepositorySupport
           implements ClientRepositoryCustom{
    @Override
    public List<Client> findAllJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct c from client c " +
                        "left join fetch c.rentals r " +
                        "left join fetch r.movie");
        List<Client> clients = query.getResultList();

        return clients;
    }
}
