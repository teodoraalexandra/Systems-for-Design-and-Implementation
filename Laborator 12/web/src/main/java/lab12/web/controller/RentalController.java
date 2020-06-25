package lab12.web.controller;

import lab12.core.model.Client;
import lab12.core.model.Rental;
import lab12.core.repository.ClientRepository;
import lab12.core.repository.MovieRepository;
import lab12.web.converter.Converter;
import lab12.web.converter.RentalConverter;
import lab12.web.dto.RentalDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api")
public class RentalController {
    public static final Logger log = LoggerFactory.getLogger(RentalController.class);

    private final ClientRepository clientRepository;
    private final MovieRepository movieRepository;

    //@Autowired
    //private RentalService rentalService;

    @Autowired
    private RentalConverter rentalConverter;

    @Autowired
    public RentalController(ClientRepository clientRepository, MovieRepository movieRepository) {
        this.clientRepository = clientRepository;
        this.movieRepository = movieRepository;
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public List<Client> getRentals() {
        /*List<Rental> rentals = this.clientRepository.findAll().stream()
                .map(Client::getRentals)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return rentals;*/
        return clientRepository.findAllWithMovies();
    }

   /* @RequestMapping(value = "/rentals", method = RequestMethod.POST)
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
    }*/
}


