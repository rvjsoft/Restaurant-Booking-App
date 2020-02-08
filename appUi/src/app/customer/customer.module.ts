import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddAddressComponent } from './add-address/add-address.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SearchRestaurantComponent } from './search-restaurant/search-restaurant.component';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';


@NgModule({
  declarations: [AddAddressComponent, SearchRestaurantComponent, RestaurantListComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    AddAddressComponent,
    SearchRestaurantComponent
  ]
})
export class CustomerModule { }
