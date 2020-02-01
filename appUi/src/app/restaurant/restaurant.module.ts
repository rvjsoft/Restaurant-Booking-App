import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RestaurantComponent } from './restaurant/restaurant.component';



@NgModule({
  declarations: [RestaurantComponent],
  imports: [
    CommonModule
  ],
  exports: [
    RestaurantComponent
  ]
})
export class RestaurantModule { }
