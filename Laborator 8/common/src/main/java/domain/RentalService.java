package domain;

import java.io.Serializable;
import java.util.List;

public interface RentalService extends Serializable{

    List<Rental> findAllRentals();
    Rental findRental(Long id);
    void addRental(Rental Rental);
    void updateRental(Rental Rental);
    void deleteRental(Long id);
}