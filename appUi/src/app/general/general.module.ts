import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FoodListComponent } from './food-list/food-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FoodEditComponent } from './food-edit/food-edit.component';
import { OrdersComponent } from './orders/orders.component';


@NgModule({
  declarations: [FoodListComponent, FoodEditComponent, OrdersComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    FoodListComponent,
    FoodEditComponent,
    OrdersComponent
  ]
})
export class GeneralModule { }
