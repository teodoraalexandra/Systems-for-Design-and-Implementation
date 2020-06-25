package lab9.service;

import lab9.model.Client;
import lab9.model.Rental;
import lab9.validators.Validator;
import lab9.validators.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lab9.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public void saveRental(Rental rental) {
        Optional<Rental> findRental= rentalRepository.findById(rental.getId());
        if (findRental.isPresent()) {
            throw new ValidatorException("Id is already taken");
        }

        try {
            log.trace("saveRental - method entered: rental={}", rental);
            rentalRepository.save(rental);
            log.trace("saveRental - method finished");
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateRental(Rental rental) {
        Optional<Rental> findRental = rentalRepository.findById(rental.getId());
        if (findRental.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        try {
            log.trace("updateRental - method entered: rental={}", rental);
            System.out.println("service " + rental);
            rentalRepository.findById(rental.getId())
                    .ifPresent(r -> {
                        r.setCid(rental.getCid());
                        r.setMid(rental.getMid());
                        log.debug("updateRental - updated: rental={}", r);
                    });
            log.trace("updateRental - method finished");
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

        log.trace("deleteRental - method entered: id={}", id);
        rentalRepository.deleteById(id);
        log.trace("deleteRental - method finished");
    }
}

