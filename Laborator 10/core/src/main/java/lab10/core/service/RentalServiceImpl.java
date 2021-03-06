package lab10.core.service;

import lab10.core.model.Rental;
import lab10.core.repository.RentalRepository;
import lab10.core.validators.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {
    private static final Logger log = LoggerFactory.getLogger(RentalServiceImpl.class);
    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public List<Rental> getAllRentals() {
        //OLD LAB IMPLEMENTATION
        return rentalRepository.findAll();

        //NEW SORT IMPLEMENTATION
        //Sort sort = new Sort("cid");
        //return rentalRepository.findAll(sort);
    }

    @Override
    public Rental saveRental(Rental rental) {
        log.trace("RentalServiceImpl - saveRental - method entered: rental={}", rental);
        return rentalRepository.save(rental);
    }

    @Override
    @Transactional
    public Rental updateRental(Long id, Rental rental) {
        Optional<Rental> findRental = rentalRepository.findById(rental.getId());
        if (findRental.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        try {
            log.trace("RentalServiceImpl - updateRental - method entered: rental={}", rental);

            Rental update = rentalRepository.findById(id).orElse(rental);
            update.setCid(rental.getCid());
            update.setMid(rental.getMid());

            return update;
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        }
    }

    @Override
    public void deleteRentalById(Long id) {
        Optional<Rental> findRental = rentalRepository.findById(id);
        if (findRental.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        log.trace("RentalServiceImpl - deleteRental - method entered: id={}", id);
        rentalRepository.deleteById(id);
        log.trace("RentalServiceImpl - deleteRental - method finished");
    }
}


