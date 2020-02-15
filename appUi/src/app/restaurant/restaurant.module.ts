import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RestaurantComponent } from './restaurant/restaurant.component';
import { GeneralModule } from '../general/general.module';
import { ModifyTablesComponent } from './modify-tables/modify-tables.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableListComponent } from './table-list/table-list.component';


@NgModule({
  declarations: [RestaurantComponent, ModifyTablesComponent, TableListComponent],
  imports: [
    CommonModule,
    GeneralModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    RestaurantComponent,
    ModifyTablesComponent,
    TableListComponent
  ]
})
export class RestaurantModule { }
