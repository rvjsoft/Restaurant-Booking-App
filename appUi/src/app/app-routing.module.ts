import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { CustomerRegistrationComponent } from './register-user/customer-registration/customer-registration.component';
import { registerLocaleData } from '@angular/common';
import { RestaurantRegistrationComponent } from './register-user/restaurant-registration/restaurant-registration.component';
import { AddAddressComponent } from './customer/add-address/add-address.component';
import { RestaurantComponent } from './restaurant/restaurant/restaurant.component';
import { FoodEditComponent } from './general/food-edit/food-edit.component';
import { ModifyTablesComponent } from './restaurant/modify-tables/modify-tables.component';


const routes: Routes = [
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'register/customer', component: CustomerRegistrationComponent
  },
  {
    path: 'register/restaurant', component: RestaurantRegistrationComponent
  },
  {
    path: 'customer/addaddress', component: AddAddressComponent
  },
  {
    path: 'restaurant', component: RestaurantComponent
  },
  {
    path: 'restaurant/editfood', component: FoodEditComponent
  },
  {
    path: 'restaurant/tables', component: ModifyTablesComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
