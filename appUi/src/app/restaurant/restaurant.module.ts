import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RestaurantComponent } from './restaurant/restaurant.component';
import { GeneralModule } from '../general/general.module';
import { ModifyTablesComponent } from './modify-tables/modify-tables.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableListComponent } from './table-list/table-list.component';
import { ResLandingComponent } from './res-landing/res-landing.component';
import { RestaurantRoutingModule } from './restaurant-routing.module';


@NgModule({
  declarations: [RestaurantComponent, ModifyTablesComponent, TableListComponent, ResLandingComponent],
  imports: [
    CommonModule,
    GeneralModule,
    FormsModule,
    ReactiveFormsModule,
    RestaurantRoutingModule
  ],
  exports: [
    RestaurantComponent,
    ModifyTablesComponent,
    TableListComponent
  ]
})
export class RestaurantModule { }
