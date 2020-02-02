import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RestaurantComponent } from './restaurant/restaurant.component';
import { GeneralModule } from '../general/general.module';



@NgModule({
  declarations: [RestaurantComponent],
  imports: [
    CommonModule,
    GeneralModule
  ],
  exports: [
    RestaurantComponent
  ]
})
export class RestaurantModule { }
