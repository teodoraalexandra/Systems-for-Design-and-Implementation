package repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import domain.Movie;
import java.util.List;
import java.util.Optional;

public class MovieRepositoryImpl implements MovieRepo {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Optional<Movie> findOne(Long ID) {

        String sql = "SELECT * FROM movie WHERE ID=?";
        return Optional.ofNullable(jdbcOperations.queryForObject(sql, new Object[] { ID }, (rs, i) -> {
            return new Movie(rs.getString("SerialNumber"), rs.getString("Title"), rs.getString("Director"), rs.getInt("Duration"));
        }));
    }

    @Override
    public List<Movie> findAllMovies() {
        String sql = "SELECT * FROM movie";
        return jdbcOperations.query(sql, (rs, i) -> {
            Long ID = Long.valueOf(rs.getInt("ID"));
            String serialNumber = rs.getString("SerialNumber");
            String title = rs.getString("Title");
            String director = rs.getString("Director");
            int duration = rs.getInt("Duration");
            Movie Movie = new Movie(serialNumber, title, director, duration);
            Movie.setId(ID);
            return Movie;
        });
    }

    @Override
    public void save(Movie Movie) {
        String sql = "INSERT INTO movie (ID, SerialNumber, Title, Director, Duration) VALUES (?,?,?,?,?)";
        jdbcOperations.update(sql, Movie.getId(), Movie.getSerial(), Movie.getTitle(), Movie.getDirector(), Movie.getDuration());
    }

    @Override
    public void update(Movie Movie) {
        String sql = "UPDATE movie SET SerialNumber=?, Title=?, Director=?, Duration=?  WHERE ID=?";
        jdbcOperations.update(sql, Movie.getSerial(), Movie.getTitle(), Movie.getDirector(), Movie.getDuration(),
                Integer.valueOf(Movie.getId().toString()));
    }

    @Override
    public void delete(Long ID) {
        String sql = "DELETE FROM movie WHERE ID = ?";
        jdbcOperations.update(sql, ID);
    }
}