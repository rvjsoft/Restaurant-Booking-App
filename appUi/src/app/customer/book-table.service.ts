import { Injectable } from '@angular/core';
import { TableAvailModel } from '../FoodOrderApp';

@Injectable({
  providedIn: 'root'
})
export class BookTableService {

  availability: { [date: string]: { [part: string]: TableAvailModel } };
  baseCount: number;
  resId: number;

  constructor() { }
}
