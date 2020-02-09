import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddAddressComponent } from './add-address/add-address.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SearchRestaurantComponent } from './search-restaurant/search-restaurant.component';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';
import { OrderCheckoutComponent } from './order-checkout/order-checkout.component';
import { GeneralModule } from '../general/general.module';


@NgModule({
  declarations: [AddAddressComponent, SearchRestaurantComponent, RestaurantListComponent, OrderCheckoutComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    GeneralModule
  ],
  exports: [
    AddAddressComponent,
    SearchRestaurantComponent,
    OrderCheckoutComponent
  ]
})
export class CustomerModule { }
