import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-rentals',
  templateUrl: './rentals.component.html',
  styleUrls: ['./rentals.component.css']
})
export class RentalsComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  addNewRental() {
    console.log("Add new rental button clicked ");

    this.router.navigate(["rental/new"]);
  }

}
