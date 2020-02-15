import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddAddressComponent } from './add-address/add-address.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SearchRestaurantComponent } from './search-restaurant/search-restaurant.component';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';
import { OrderCheckoutComponent } from './order-checkout/order-checkout.component';
import { GeneralModule } from '../general/general.module';
import { BookTableComponent } from './book-table/book-table.component';
import { RestaurantModule } from '../restaurant/restaurant.module';


@NgModule({
  declarations: [AddAddressComponent, SearchRestaurantComponent, RestaurantListComponent, OrderCheckoutComponent, BookTableComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    GeneralModule,
    RestaurantModule
  ],
  exports: [
    AddAddressComponent,
    SearchRestaurantComponent,
    OrderCheckoutComponent,
    BookTableComponent
  ]
})
export class CustomerModule { }
