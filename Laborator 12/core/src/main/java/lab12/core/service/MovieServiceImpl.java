package lab12.core.service;

import lab12.core.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class MovieServiceImpl extends EntityServiceImpl<Movie> {

    @Autowired
    public MovieServiceImpl(JpaRepository<Movie, Long> repository) {
        super(repository);
    }

}
