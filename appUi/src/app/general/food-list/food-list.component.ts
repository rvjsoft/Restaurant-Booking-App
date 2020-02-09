import { Component, OnInit, OnChanges, Input, ChangeDetectionStrategy, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { FoodModel } from 'src/app/FoodOrderApp';
import { AppServiceService } from 'src/app/app-service.service';
import { DomSanitizer } from '@angular/platform-browser';
import { FormBuilder, FormControl } from '@angular/forms';
import { Observable, Observer, of } from 'rxjs';
import { SessionService } from 'src/app/session.service';

@Component({
  selector: 'app-food-list',
  templateUrl: './food-list.component.html',
  styleUrls: ['./food-list.component.css']
})
export class FoodListComponent implements OnInit, OnChanges {

  private foodImage = '/assets/images/food.svg';
  imageVeg = '/assets/images/veg.svg';
  imageNonVeg = '/assets/images/non_veg.svg';
  private quantityValues: any = {};
  private foodImages: any = {};
  private temp: any;
  readonly res;

  @Input()
  isEdit: boolean;
  @Input()
  private foodList: Array<FoodModel>;
  @Output()
  private editFood = new EventEmitter<FoodModel>();
  @Output()
  private deleteFood = new EventEmitter<FoodModel>();

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

  print() {
    console.log(this.quantityValues);
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

}
