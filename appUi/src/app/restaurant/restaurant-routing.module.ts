import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RestaurantComponent } from './restaurant/restaurant.component';
import { ResLandingComponent } from './res-landing/res-landing.component';
import { OrdersComponent } from '../general/orders/orders.component';
import { FoodEditComponent } from '../general/food-edit/food-edit.component';
import { ModifyTablesComponent } from './modify-tables/modify-tables.component';
import { TablesHistoryComponent } from '../general/tables-history/tables-history.component';


const routes: Routes = [
  {
    path: 'res', component: ResLandingComponent,
    children: [
      {
        path: 'restaurant', component: RestaurantComponent
      },
      {
        path: 'orders', component: OrdersComponent
      },
      {
        path: 'editfood', component: FoodEditComponent
      },
      {
        path: 'tables', component: ModifyTablesComponent
      },
      {
        path: 'bookings', component: TablesHistoryComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RestaurantRoutingModule { }
