import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FoodListComponent } from './food-list/food-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FoodEditComponent } from './food-edit/food-edit.component';


@NgModule({
  declarations: [FoodListComponent, FoodEditComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    FoodListComponent,
    FoodEditComponent
  ]
})
export class GeneralModule { }
