import { Injectable } from '@angular/core';
import { FoodModel } from '../FoodOrderApp';

@Injectable({
  providedIn: 'root'
})
export class OrderCheckoutService {

  foods: Array<FoodModel>;
  resId: number;
  quantity : any;

  constructor() { }

  cleanup() {
    this.foods = null;
    this.quantity = null;
    this.resId = null;
  }
}
