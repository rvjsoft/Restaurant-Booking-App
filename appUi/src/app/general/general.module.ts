import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FoodListComponent } from './food-list/food-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FoodEditComponent } from './food-edit/food-edit.component';
import { OrdersComponent } from './orders/orders.component';
import { CustomerOrdersComponent } from './customer-orders/customer-orders.component';
import { TablesHistoryComponent } from './tables-history/tables-history.component';


@NgModule({
  declarations: [FoodListComponent, FoodEditComponent, OrdersComponent, CustomerOrdersComponent, TablesHistoryComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    FoodListComponent,
    FoodEditComponent,
    OrdersComponent,
    CustomerOrdersComponent,
    TablesHistoryComponent
  ]
})
export class GeneralModule { }
