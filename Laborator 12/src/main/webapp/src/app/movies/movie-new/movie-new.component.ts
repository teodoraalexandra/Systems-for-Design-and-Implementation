import { Component, OnInit } from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Location} from "@angular/common";
import swal from "sweetalert";

@Component({
  selector: 'app-movie-new',
  templateUrl: './movie-new.component.html',
  styleUrls: ['./movie-new.component.css']
})
export class MovieNewComponent implements OnInit {

  constructor(private movieService: MovieService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  saveMovie(serialNumber: string, title: string, director: string, duration: string) {
    console.log("Saving movie", serialNumber, title, director, duration);

    let maybeNumber;
    maybeNumber = Number(duration);

    if (serialNumber == null || title == null || director == null || duration == null ||
      serialNumber == "" || title == "" || director == "" || duration == "") {
      swal("Please enter values in all fields.");
    } else if (isNaN(maybeNumber)) {
      swal("Duration must be a number.");
    } else {
      this.movieService.saveMovie({
        id: 0,
        serialNumber,
        title,
        director,
        duration: +duration
      })
        .subscribe(movie => console.log("saved movie: ", movie));

      this.location.back();
    }
  }
}
