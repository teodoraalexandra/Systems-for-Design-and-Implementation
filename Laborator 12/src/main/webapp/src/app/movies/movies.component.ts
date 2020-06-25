import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  addNewMovie() {
    console.log("Add new movie button clicked ");

    this.router.navigate(["movie/new"]);
  }

}
