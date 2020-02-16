import { Component, OnInit, SimpleChanges, Input, OnChanges, ViewChild, ViewChildren, QueryList, AfterViewChecked } from '@angular/core';
import { FoodModel, OrderFoodRequest } from 'src/app/FoodOrderApp';
import { ToastService } from 'src/app/ui-components/toast.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AppServiceService } from 'src/app/app-service.service';
import { FoodListComponent } from 'src/app/general/food-list/food-list.component';
import { OrderCheckoutService } from '../order-checkout.service';
import { AddressService } from '../address.service';

@Component({
  selector: 'app-order-checkout',
  templateUrl: './order-checkout.component.html',
  styleUrls: ['./order-checkout.component.css']
})
export class OrderCheckoutComponent implements OnInit, OnChanges, AfterViewChecked {

  @Input()
  foods: Array<FoodModel>;
  quantity: {};
  temp: any;
  total;
  resId;

  @ViewChildren(FoodListComponent) foodList !: QueryList<FoodListComponent>;

  constructor(
    private toastService: ToastService,
    private appService: AppServiceService,
    private route: ActivatedRoute,
    private router: Router,
    private orderService: OrderCheckoutService,
    private addressService: AddressService
  ) { }

  ngOnInit() {
    // this.temp = JSON.parse(history.state['foods']);
    // this.foods = this.temp;
    // this.temp = JSON.parse(history.state['quantity']);
    // this.quantity = this.temp;
    // this.resId = history.state['resId'];
    this.foods = this.orderService.foods;
    this.quantity = this.orderService.quantity;
    this.resId = this.orderService.resId;
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes);
  }

  ngAfterViewChecked(): void {
    setTimeout( () => this.total = this.foodList.last._total, 100);
  }

  get priceTotal() {
    return this.total;
  }

  public checkout() {
    let request = new OrderFoodRequest();
    request.resId = this.resId;
    let requestFoods = {};
    let foods = this.foodList.last.quantityValues;
    console.log(foods);
    if(foods == null || foods == undefined) {
      this.toastService.showMessage(['no food selected'], true);
      return;
    }
    if(this.address == null || this.address == undefined) {
      this.toastService.showMessage(['no address selected'], true);
      return;
    }
    for(let val of Object.keys(foods)) {
      if(foods[val+''] != 0){
        requestFoods[val+''] = foods[val+''];
      }
    }
    request.foods = requestFoods;
    if(Object.keys(requestFoods).length == 0) {
      this.toastService.showMessage(['no food selected'], true);
      return;
    }
    this.appService.orderFood(request).subscribe(
      (response) => {
        this.toastService.showMessage([response.message], false);
        // this.router.navigate(['customer/search']);
        this.orderService.cleanup();
        this.router.navigate(['customer/home']);
      },
      (error: any) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

  private extractErrorMesage(errorObj: any): string[] {
    let messages: string[] = [];
    if (errorObj == null || errorObj == undefined) {
      messages.push('service not available');
      return messages ;
    }
    messages.push(errorObj.message);
    let errors = errorObj.errors;
    for(let error in errors) {
      messages.push(errors[error]);
    }

    return messages;
  }

  get address() {
    return this.addressService.deliveryAddress;
  }

  public selectAddress() {
    this.router.navigate(['/customer/addaddress']);
  }
}
