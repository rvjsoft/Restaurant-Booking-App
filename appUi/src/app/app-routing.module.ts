import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { CustomerRegistrationComponent } from './register-user/customer-registration/customer-registration.component';
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
  },
  {
    path: '*', redirectTo: 'login'
  }/*,
  {
    path: '**', redirectTo: 'login'
  }/* ,
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
  },
  {
    path: 'customer/search', component: SearchRestaurantComponent
  },
  {
    path: 'customer/checkout', component: OrderCheckoutComponent
  },
  {
    path: 'restaurant/orders', component: OrdersComponent
  },
  {
    path: 'customer/orders', component: CustomerOrdersComponent
  },
  {
    path: 'restaurant/bookings', component: TablesHistoryComponent
  },
  {
    path: 'customer/bookings', component: TablesHistoryComponent
  },
  {
    path: 'customer/booktable', component: BookTableComponent
  } */
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
