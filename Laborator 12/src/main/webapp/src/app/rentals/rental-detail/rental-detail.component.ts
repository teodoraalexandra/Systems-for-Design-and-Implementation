import {Component, Input, OnInit} from '@angular/core';
import {RentalService} from "../shared/rental.service";
import {Rental} from "../shared/rental.model";

import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';

import {switchMap} from "rxjs/operators";
import {Client} from "../../clients/shared/client.model";
import {Movie} from "../../movies/shared/movie.model";

import { MovieService } from "../../movies/shared/movie.service";
import { ClientService } from "../../clients/shared/client.service";

@Component({
  selector: 'app-rental-detail',
  templateUrl: './rental-detail.component.html',
  styleUrls: ['./rental-detail.component.css']
})
export class RentalDetailComponent implements OnInit {
  errorMessage: string;
  clients: Array<Client>;
  selectedClient: Client = null;
  movies: Array<Movie>;
  selectedMovie: Movie = null;

  @Input() rental: Rental;

  constructor(private rentalService: RentalService,
              private clientService: ClientService,
              private movieService: MovieService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.rentalService.getRental(+params['id'])))
      .subscribe(rental => this.rental = rental);
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
    this.rental.clientId = client.id;
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
    this.rental.movieId = movie.id;
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.rentalService.update(this.rental)
      .subscribe(_ => this.goBack());
  }

}
