package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.*;
import repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceServer implements RentalService {

   @Autowired
    private RentalRepo rentalRepo;

    @Override
    public List<Rental> findAllRentals() {
        List<Rental> result = rentalRepo.findAllRentals();
        System.out.println("RentalServiceServer: findAllRentals() = " + result);

        return result;
    }

    @Override
    public Rental findRental(Long id) {
        Rental rental = rentalRepo.findOne(id).get();
        System.out.println("RentalServiceServer: findRental = " + rental);

        return rental;
    }

    @Override
    public void addRental(Rental rental) {
        rentalRepo.save(rental);

        System.out.println("Add successfully! -- RentalServiceServer");
    }

    @Override
    @Transactional
    public void updateRental(Rental rental) {
        Optional<Rental> rentalOptional = rentalRepo.findOne(rental.getId());

        if (rentalOptional.isPresent()) {
            rentalRepo.update(rental);
        }

        System.out.println("Update successfully: RentalServiceServer");
    }

    @Override
    public void deleteRental(Long id) {
        rentalRepo.delete(id);
        System.out.println("Delete successfully: RentalServiceServer");
    }


}