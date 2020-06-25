package lab10.core.service;

import lab10.core.model.Rental;

import java.util.List;

public interface RentalService {
    List<Rental> getAllRentals();

    Rental saveRental(Rental rental);

    Rental updateRental(Long id, Rental rental);

    void deleteRentalById(Long id);
}
