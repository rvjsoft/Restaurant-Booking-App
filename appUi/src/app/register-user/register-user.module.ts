import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomerRegistrationComponent } from './customer-registration/customer-registration.component';
import { RestaurantRegistrationComponent } from './restaurant-registration/restaurant-registration.component';



@NgModule({
  declarations: [CustomerRegistrationComponent, RestaurantRegistrationComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    CustomerRegistrationComponent,
    RestaurantRegistrationComponent
  ]
})
export class RegisterUserModule { }
