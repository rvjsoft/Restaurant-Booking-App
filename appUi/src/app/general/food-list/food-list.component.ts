import { Component, OnInit, OnChanges, Input, ChangeDetectionStrategy, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { FoodModel } from 'src/app/FoodOrderApp';
import { AppServiceService } from 'src/app/app-service.service';
import { DomSanitizer } from '@angular/platform-browser';
import { FormBuilder, FormControl } from '@angular/forms';
import { Observable, Observer, of, from } from 'rxjs';
import { SessionService } from 'src/app/session.service';
import { tap, filter } from 'rxjs/operators';

@Component({
  selector: 'app-food-list',
  templateUrl: './food-list.component.html',
  styleUrls: ['./food-list.component.css']
})
export class FoodListComponent implements OnInit, OnChanges {

  private foodImage = '/assets/images/food.svg';
  imageVeg = '/assets/images/veg.svg';
  imageNonVeg = '/assets/images/non_veg.svg';
  private foodImages: any = {};
  private temp: any;
  readonly res;
  public total: number;

  @Input()
  isEdit: boolean;
  @Input()
  private foodList: Array<FoodModel>;
  @Input()
  public quantityValues: any = {};
  @Input()
  private isCheckout: boolean;
  @Input()
  private isAvailable: boolean;
  @Output()
  private editFood = new EventEmitter<FoodModel>();
  @Output()
  private deleteFood = new EventEmitter<FoodModel>();
  @Output()
  private checkoutOrder = new EventEmitter<any>();

  constructor(private appService: AppServiceService,
    private sanitizer: DomSanitizer,
    private fb: FormBuilder,
    private session: SessionService
    ) {
        this.res = this.isRes;
     }

  ngOnInit() {
    console.log('inside init', this.foodList);
    for (let index in this.foodList) {
      let food = this.foodList[index];
      if(this.quantityValues[food.id+''] == null || this.quantityValues[food.id+''] == undefined)
      this.quantityValues[food.id+''] = 0;
    }
    for(let index in this.foodList) {
      let food = this.foodList[index];
      if(food.imageId == null || food.imageId == undefined)
        this.foodImages[food.id] = of(this.foodImage);
      this.foodImages[food.id] = new Observable<any>(
        (observer: Observer<any>) => {
            this.appService.getRestaurantImage(food.imageId).subscribe(
              (imageData) => {
                observer.next(imageData);
              }, (error) => {
                observer.next(this.foodImage);
              }
            );
          }
      );
    }
    this.setTotal();
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes['foodList']);
    let foods = changes['foodList'].currentValue
    for (let index in foods) {
      let food = foods[index];
      if (this.quantityValues[food.id + ''] == null || this.quantityValues[food.id + ''] == undefined){
        this.quantityValues[food.id+''] = 0;
      }
    }
    for(let index in this.foodList) {
      let food = this.foodList[index];
      if(food.imageId == null || food.imageId == undefined)
        this.foodImages[food.id] = of('url(' + this.foodImage + ')');
      this.foodImages[food.id] = new Observable<any>(
        (observer: Observer<any>) => {
            this.appService.getRestaurantImage(food.imageId).subscribe(
              (imageData) => {
                observer.next(imageData);
              }, (error) => {
                observer.next(this.foodImage);
              }
            );
          }
      );
    }
  }

  trackByFn(index, item) {
    return item.id;
  }

  private edit(foodData: FoodModel) {
    this.editFood.emit(foodData);
  }

  private delete(foodData: FoodModel) {
    this.deleteFood.emit(foodData);
  }

  get isRes() {
    if(this.session.userLevel == 'RESTAURANT') {
      return true;
    } else {
      return false;
    }
  }

  public checkout() {
    let orderedFood = Array<FoodModel>();
    for(let id of Object.keys(this.quantityValues)) {
      if(this.quantityValues[id] != 0) {
        for(let val of this.foodList) {
          if(val.id+'' == id) {
            orderedFood.push(val);
            break;
          }
        }
      }
    }
    let eventData = {};
    eventData['foods'] = orderedFood;
    eventData['quantity'] = this.quantityValues;
    this.checkoutOrder.emit(eventData);
  }

  setTotal() {
    this.total = 0;
    for(let food of this.foodList) {
      this.total = this.total + (food.price * this.quantityValues[food.id+'']);
    }
  }

  get _total() {
    return this.total;
  }

}
