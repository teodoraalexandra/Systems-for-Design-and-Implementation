package lab12.core.repository;

import lab12.core.model.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("movieRepository")
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select distinct m from Movie m")
    @EntityGraph(value = "movieWithClients",
            type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> findAllWithClients();
}
