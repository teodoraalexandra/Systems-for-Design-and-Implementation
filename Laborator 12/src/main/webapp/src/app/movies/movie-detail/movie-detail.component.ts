import { Component, Input, OnInit } from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Movie} from "../shared/movie.model";

import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';

import {switchMap} from "rxjs/operators";
import swal from "sweetalert";

@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit {

  @Input() movie: Movie;

  constructor(private movieService: MovieService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.movieService.getMovie(+params['id'])))
      .subscribe(movie => this.movie = movie);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    let serialNumber = this.movie.serialNumber;
    let title = this.movie.title;
    let director = this.movie.director;
    let duration = this.movie.duration;

    let maybeNumber;
    maybeNumber = Number(duration);

    if (serialNumber == null || title == null || director == null || duration == null ||
      serialNumber == "" || title == "" || director == "") {
      swal("Please enter values in all fields.");
    } else if (isNaN(maybeNumber)) {
      swal("Duration must be a number.");
    } else {
      this.movieService.update(this.movie)
        .subscribe(_ => this.goBack());
    }
  }

}
