package repository;

import java.util.List;
import java.util.Optional;

import domain.Rental;

public interface RentalRepo {
    List<Rental> findAllRentals();
    void save(Rental assign);
    void update(Rental assign);
    void delete(Long id);
    Optional<Rental> findOne(Long id);
}