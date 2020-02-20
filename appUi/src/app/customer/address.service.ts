import { Injectable } from '@angular/core';
import { AddressModel } from '../FoodOrderApp';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  deliveryAddress: AddressModel;

  constructor() { }
}
