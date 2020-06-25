package repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import domain.Rental;

import java.util.List;
import java.util.Optional;

@Repository
public class RentalRepositoryImpl implements RentalRepo{

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Optional<Rental> findOne(Long ID) {

        String sql = "SELECT * FROM rental WHERE ID=?";
        return Optional.ofNullable(jdbcOperations.queryForObject(sql, new Object[]{ID}, (rs, i) -> {
            return new Rental(rs.getString("CID"), rs.getString("MID"));
        }));
    }

    @Override
    public List<Rental> findAllRentals() {
        String sql = "SELECT * FROM rental";
        return jdbcOperations.query(sql, (rs, i) -> {
            Long ID = Long.valueOf(rs.getInt("ID"));
            String CID = rs.getString("CID");
            String MID = rs.getString("MID");
            Rental Rental = new Rental(CID,MID);
            Rental.setId(ID);
            return Rental;
        });
    }

    @Override
    public void save(Rental Rental) {
        String sql = "INSERT INTO rental (ID, CID, MID) VALUES (?,?,?)";
        jdbcOperations.update(sql, Rental.getId(), Rental.getClientId(), Rental.getMovieId());
    }

    @Override
    public void update(Rental Rental) {
        String sql = "UPDATE rental SET CID=?, MID=? WHERE ID=?";
        jdbcOperations.update(sql, Rental.getClientId(), Rental.getMovieId(),
                Integer.valueOf(Rental.getId().toString()));
    }

    @Override
    public void delete(Long ID) {
        String sql = "DELETE FROM rental WHERE ID = ?";
        jdbcOperations.update(sql, ID);
    }
}