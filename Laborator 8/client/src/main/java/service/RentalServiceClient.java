package service;

import org.springframework.beans.factory.annotation.Autowired;

import domain.*;
import java.util.List;
import java.util.stream.Collectors;

public class RentalServiceClient implements RentalService {

    @Autowired
    RentalService RentalService;

    @Override
    public Rental findRental(Long id)  {
        System.out.println("Client ---> call method findRental(" + id + ")\n");
        return RentalService.findRental(id);
    }

    @Override
    public void addRental(Rental Rental)  {
        System.out.println("Client ---> call method addRental(" + Rental + ")\n");
        RentalService.addRental(Rental);
    }

    @Override
    public void updateRental(Rental Rental)  {
        System.out.println("Client ---> call method updateRental(" + Rental + ")\n");
        RentalService.updateRental(Rental);
    }

    @Override
    public void deleteRental(Long id)  {
        System.out.println("Client ---> call method deleteRental(" + id + ")\n");
        RentalService.deleteRental(id);
    }

    @Override
    public List<Rental> findAllRentals()  {
        System.out.println("Client ---> call method findAllRentals()\n");
        return RentalService.findAllRentals();
    }
}