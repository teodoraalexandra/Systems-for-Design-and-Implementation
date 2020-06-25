package lab9.service;

import lab9.model.Rental;
import java.util.List;


public interface RentalService {
    List<Rental> getAllRentals();

    void saveRental(Rental rental);

    void updateRental(Rental rental);

    void deleteRentalById(Long id);
}
