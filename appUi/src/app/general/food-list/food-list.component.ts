import { Component, OnInit, OnChanges, Input, ChangeDetectionStrategy, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { FoodModel, FoodStatusRequest } from 'src/app/FoodOrderApp';
import { AppServiceService } from 'src/app/app-service.service';
import { DomSanitizer } from '@angular/platform-browser';
import { FormBuilder, FormControl } from '@angular/forms';
import { Observable, Observer, of, from } from 'rxjs';
import { SessionService } from 'src/app/session.service';
import { tap, filter } from 'rxjs/operators';
import { Status } from 'src/app/AppEnums';
import { ToastService } from 'src/app/ui-components/toast.service';
import { LoadBarService } from 'src/app/load-bar.service';
import { ImageResponseCacheService } from 'src/app/image-response-cache.service';

@Component({
  selector: 'app-food-list',
  templateUrl: './food-list.component.html',
  styleUrls: ['./food-list.component.css']
})
export class FoodListComponent implements OnInit, OnChanges {

  public foodImage = '/assets/images/food.svg';
  imageVeg = '/assets/images/veg.svg';
  imageNonVeg = '/assets/images/non_veg.svg';
  public foodImages: any = {};
  public temp: any;
  readonly res;
  public total: number;
  available: number[] = [];
  unavailable: number[] = [];

  @Input()
  isEdit: boolean;
  @Input()
  public foodList: Array<FoodModel>;
  @Input()
  public quantityValues: any = {};
  @Input()
  public isCheckout: boolean;
  @Input()
  public isAvailable: boolean;
  @Input()
  public isModify: boolean;
  @Output()
  public editFood = new EventEmitter<FoodModel>();
  @Output()
  public deleteFood = new EventEmitter<FoodModel>();
  @Output()
  public checkoutOrder = new EventEmitter<any>();

  constructor(private appService: AppServiceService,
    private sanitizer: DomSanitizer,
    private fb: FormBuilder,
    public session: SessionService,
    private toastService: ToastService,
    public loadBar: LoadBarService,
    private imageCache: ImageResponseCacheService
  ) {
    this.res = this.isRes;
  }

  ngOnInit() {
    console.log('inside init', this.foodList);
    for (let index in this.foodList) {
      let food = this.foodList[index];
      if (this.quantityValues[food.id + ''] == null || this.quantityValues[food.id + ''] == undefined)
        this.quantityValues[food.id + ''] = 0;
    }
    for (let index in this.foodList) {
      let food = this.foodList[index];
      if (food.imageId == null || food.imageId == undefined)
        this.foodImages[food.id] = of(this.foodImage);
      this.foodImages[food.id] = new Observable<any>(
        (observer: Observer<any>) => {
          let cachedImage = this.imageCache.getImage(food.imageId);
          if (cachedImage != null && cachedImage != undefined) {
            observer.next(cachedImage);
          } else {
            this.appService.getRestaurantImage(food.imageId).subscribe(
              (imageData) => {
                this.imageCache.setImage(food.imageId, imageData);
                observer.next(imageData);
              }, (error) => {
                observer.next(this.foodImage);
              }
            );
          }
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
      if (this.quantityValues[food.id + ''] == null || this.quantityValues[food.id + ''] == undefined) {
        this.quantityValues[food.id + ''] = 0;
      }
    }
    for (let index in this.foodList) {
      let food = this.foodList[index];
      if (food.imageId == null || food.imageId == undefined)
        this.foodImages[food.id] = of('url(' + this.foodImage + ')');
      this.foodImages[food.id] = new Observable<any>(
        (observer: Observer<any>) => {
          let cachedImage = this.imageCache.getImage(food.imageId);
          if (cachedImage != null && cachedImage != undefined) {
            observer.next(cachedImage);
          } else {
            this.appService.getRestaurantImage(food.imageId).subscribe(
              (imageData) => {
                this.imageCache.setImage(food.imageId, imageData);
                observer.next(imageData);
              }, (error) => {
                observer.next(this.foodImage);
              }
            );
          }
        }
      );
    }
  }

  trackByFn(index, item) {
    return item.id;
  }

  public edit(foodData: FoodModel) {
    this.editFood.emit(foodData);
  }

  public delete(foodData: FoodModel) {
    this.deleteFood.emit(foodData);
  }

  get isRes() {
    if (this.session.userLevel == 'RESTAURANT') {
      return true;
    } else {
      return false;
    }
  }

  public checkout() {
    let orderedFood = Array<FoodModel>();
    for (let id of Object.keys(this.quantityValues)) {
      if (this.quantityValues[id] != 0) {
        for (let val of this.foodList) {
          if (val.id + '' == id) {
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
    if (this.foodList == null) return;
    for (let food of this.foodList) {
      this.total = this.total + (food.price * this.quantityValues[food.id + '']);
    }
  }

  get _total() {
    return this.total;
  }

  public print(val: any) {
    console.log(val);
  }

  public alterStatus(id: number, status: Status) {
    if (status == Status.AVAILABLE) {
      this.available.push(id);
      if (this.unavailable.indexOf(id) !== -1) {
        this.unavailable.splice(this.unavailable.indexOf(id), 1);
      }
    } else {
      this.unavailable.push(id);
      if (this.available.indexOf(id) !== -1) {
        this.available.splice(this.available.indexOf(id), 1);
      }
    }
    console.log(this.available);
    console.log(this.unavailable);
  }

  public updateStatus() {
    let request = new FoodStatusRequest();
    request.available = this.available;
    request.unavailable = this.unavailable;
    this.appService.updateFoodStatus(request).subscribe(
      (response) => {
        this.toastService.showMessage([response.message], false);
        this.available = [];
        this.unavailable = [];
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
      return messages;
    }
    messages.push(errorObj.message);
    let errors = errorObj.errors;
    for (let error in errors) {
      messages.push(errors[error]);
    }
    return messages;
  }

}
