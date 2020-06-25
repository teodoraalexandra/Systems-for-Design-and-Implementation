import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { ClientsComponent } from "./clients/clients.component";
import { ClientDetailComponent } from "./clients/client-detail/client-detail.component";
import { ClientNewComponent} from "./clients/client-new/client-new.component";

import { MoviesComponent } from "./movies/movies.component";
import { MovieDetailComponent } from "./movies/movie-detail/movie-detail.component";
import { MovieNewComponent } from "./movies/movie-new/movie-new.component";

import { RentalsComponent } from "./rentals/rentals.component";
import { RentalDetailComponent } from "./rentals/rental-detail/rental-detail.component";
import { RentalNewComponent } from "./rentals/rental-new/rental-new.component";

const routes: Routes = [
  {path: 'clients', component: ClientsComponent},
  {path: 'client/detail/:id', component: ClientDetailComponent},
  {path: 'client/new', component: ClientNewComponent},

  {path: 'movies', component: MoviesComponent},
  {path: 'movie/detail/:id', component: MovieDetailComponent},
  {path: 'movie/new', component: MovieNewComponent},

  {path: 'rentals', component: RentalsComponent},
  {path: 'rental/detail/:id', component: RentalDetailComponent},
  {path: 'rental/new', component: RentalNewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
