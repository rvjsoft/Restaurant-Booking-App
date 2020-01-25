import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { CustomerRegistrationComponent } from './register-user/customer-registration/customer-registration.component';
import { registerLocaleData } from '@angular/common';
import { RestaurantRegistrationComponent } from './register-user/restaurant-registration/restaurant-registration.component';


const routes: Routes = [
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'register/customer', component: CustomerRegistrationComponent
  },
  {
    path: 'register/restaurant', component: RestaurantRegistrationComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
