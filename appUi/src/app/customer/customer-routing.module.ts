import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TablesHistoryComponent } from '../general/tables-history/tables-history.component';
import { CustLandingComponent } from './cust-landing/cust-landing.component';
import { SearchRestaurantComponent } from './search-restaurant/search-restaurant.component';
import { CustomerOrdersComponent } from '../general/customer-orders/customer-orders.component';
import { AddAddressComponent } from './add-address/add-address.component';
import { RestaurantComponent } from '../restaurant/restaurant/restaurant.component';
import { OrderCheckoutComponent } from './order-checkout/order-checkout.component';
import { BookTableComponent } from './book-table/book-table.component';


const routes: Routes = [
  {
    path: 'customer', component: CustLandingComponent,
    children: [
      {
        path: 'home', component: SearchRestaurantComponent
      },
      {
        path: 'orders', component: CustomerOrdersComponent
      },
      {
        path: 'bookings', component: TablesHistoryComponent
      },
      {
        path: 'addaddress', component: AddAddressComponent
      },
      {
        path: 'restaurant', component: RestaurantComponent
      },
      {
        path: 'checkout', component: OrderCheckoutComponent
      },
      {
        path: 'booktable', component: BookTableComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
