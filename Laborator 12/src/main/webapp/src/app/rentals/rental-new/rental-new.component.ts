import { Component, OnInit } from '@angular/core';
import { RentalService } from "../shared/rental.service";
import { MovieService } from "../../movies/shared/movie.service";
import { ClientService } from "../../clients/shared/client.service";
import { Location } from "@angular/common";
import { Rental } from "../shared/rental.model";
import { Movie} from "../../movies/shared/movie.model";
import { Client} from "../../clients/shared/client.model";

@Component({
  selector: 'app-rental-new',
  templateUrl: './rental-new.component.html',
  styleUrls: ['./rental-new.component.css']
})
export class RentalNewComponent implements OnInit {
  errorMessage: string;
  clients: Array<Client>;
  selectedClient: Client = null;
  movies: Array<Movie>;
  selectedMovie: Movie = null;

  constructor(private rentalService: RentalService,
              private clientService: ClientService,
              private movieService: MovieService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
    this.getClients();
    this.getMovies();
  }

  getClients() {
    this.clientService.getClients()
      .subscribe(
        clients => this.clients = clients,
        error => this.errorMessage = <any>error
      );
  }

  onSelectClient(client: Client): void {
    this.selectedClient = client;
  }

  getMovies() {
    this.movieService.getMovies()
      .subscribe(
        movies => this.movies = movies,
        error => this.errorMessage = <any>error
      );
  }

  onSelectMovie(movie: Movie): void {
    this.selectedMovie = movie;
  }

  saveRental(clientId: string, movieId: string) {
    console.log("Saving rental", clientId, movieId);

    if (this.selectedClient == null || this.selectedMovie == null) {
      alert("You must select both client and movie for adding a rental.");
    } else {
      this.rentalService.saveRental({
        id: 0,
        clientId: +this.selectedClient.id,
        movieId: +this.selectedMovie.id
      })
        .subscribe(rental => console.log("saved rental: ", rental));

      this.location.back();
    }
  }

  String(id: number) {
    return id.toString();
  }
}
