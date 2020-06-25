package lab10.web.controller;

import lab10.core.service.RentalService;
import lab10.web.converter.RentalConverter;
import lab10.web.dto.RentalDto;
import lab10.web.dto.RentalsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lab10.web.dto.MoviesDto;


@RestController
public class RentalController {
    public static final Logger log = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalConverter rentalConverter;


    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    RentalsDto getRentals() {
        log.trace("RentalController - getRenrals - enter method");
        return new RentalsDto(rentalConverter
                .convertModelsToDtos(rentalService.getAllRentals()));
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