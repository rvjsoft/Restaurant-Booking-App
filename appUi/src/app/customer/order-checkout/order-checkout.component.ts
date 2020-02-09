import { Component, OnInit, SimpleChanges, Input, OnChanges, ViewChild, ViewChildren, QueryList, AfterViewChecked } from '@angular/core';
import { FoodModel, OrderFoodRequest } from 'src/app/FoodOrderApp';
import { ToastService } from 'src/app/ui-components/toast.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AppServiceService } from 'src/app/app-service.service';
import { FoodListComponent } from 'src/app/general/food-list/food-list.component';
import { totalmem } from 'os';

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
    private router: Router
  ) { }

  ngOnInit() {
    this.temp = JSON.parse(history.state['foods']);
    this.foods = this.temp;
    this.temp = JSON.parse(history.state['quantity']);
    this.quantity = this.temp;
    this.resId = history.state['resId'];
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
    for(let val in Object.keys(foods)) {
      if(foods[val+''] != 0){
        requestFoods[val+''] = foods[val+''];
      }
    }
    request.foods = requestFoods;
    this.appService.orderFood(request).subscribe(
      (response) => {
        this.toastService.showMessage([response.message], false);
        this.router.navigate(['customer/search']);
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

}
