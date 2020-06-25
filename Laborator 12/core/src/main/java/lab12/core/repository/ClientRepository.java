package lab12.core.repository;

import lab12.core.model.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<Client, Long>,
                ClientRepositoryCustom {
    @Query("select distinct c from Client c")
    @EntityGraph(value = "clientWithMovies",
            type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllWithMovies();

}
