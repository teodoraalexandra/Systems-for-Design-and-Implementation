package lab11.web.controller;

import lab11.core.model.Rental;
import lab11.core.service.RentalService;
import lab11.web.converter.RentalConverter;
import lab11.web.dto.RentalDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class RentalController {
    public static final Logger log = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalConverter rentalConverter;

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public List<RentalDto> getRentals() {
        log.trace("RentalController - getRentals - enter method");

        List<Rental> rentals = rentalService.getAllRentals();

        return new ArrayList<>(rentalConverter.convertModelsToDtos(rentals));
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    RentalDto saveRental(@RequestBody RentalDto rentalDto) {
        log.trace("RentalController - saveRental - enter method");
        return rentalConverter.convertModelToDto(rentalService.saveRental(
                rentalConverter.convertDtoToModel(rentalDto)
        ));
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.PUT)
    RentalDto updateRental(@PathVariable Long id,
                           @RequestBody RentalDto rentalDto) {
        log.trace("RentalController - updateRental - enter method");
        return rentalConverter.convertModelToDto( rentalService.updateRental(id,
                rentalConverter.convertDtoToModel(rentalDto)));
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRental(@PathVariable Long id){
        log.trace("RentalController - deleteRental - enter method");

        rentalService.deleteRentalById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
