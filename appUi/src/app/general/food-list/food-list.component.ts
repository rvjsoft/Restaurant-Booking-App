import { Component, OnInit, OnChanges, Input, ChangeDetectionStrategy, SimpleChanges } from '@angular/core';
import { FoodModel } from 'src/app/FoodOrderApp';
import { AppServiceService } from 'src/app/app-service.service';
import { DomSanitizer } from '@angular/platform-browser';
import { FormBuilder, FormControl } from '@angular/forms';
import { Observable, Observer, of } from 'rxjs';

@Component({
  selector: 'app-food-list',
  templateUrl: './food-list.component.html',
  styleUrls: ['./food-list.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FoodListComponent implements OnInit, OnChanges {

  private foodImage = '/assets/images/food.svg';
  private quantityForm = this.fb.group({});
  private foodImages: any = {};

  @Input()
  private foodList: Array<FoodModel>;
  private temp: any;

  constructor(private appService: AppServiceService, private sanitizer: DomSanitizer, private fb: FormBuilder) { }

  ngOnInit() {
    console.log('inside init', this.foodList);
    for (let index in this.foodList) {
      let food = this.foodList[index];
      this.quantityForm.addControl(food.id + '', new FormControl(''));
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
      if (!this.quantityForm.contains(food.id + ''))
        this.quantityForm.addControl(food.id + '', new FormControl(''));
    }
    for(let index in this.foodList) {
      let food = this.foodList[index];
      if(food.imageId == null || food.imageId == undefined)
        continue;
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


  public getImage(imageId: string): Observable<any> {
    console.log('in method getImage ', imageId);
    let imageAsync = new Observable<any>(
      (observer: Observer<any>) => {
          this.appService.getRestaurantImage(imageId).subscribe(
            (imageData) => {
              observer.next(imageData);
              console.log("got data");
            }, (error) => {
              observer.next(this.foodImage);
              console.log("got error", error);
            }
          );
        }
    );
        return imageAsync;
    /* if (food.image != null && food.image != undefined)
      return food;
    this.appService.getRestaurantImage(food.imageId).subscribe(
      (imageData) => {
        food.image = imageData;
        console.log("got data");
      }, (error) => {
        food.imageId = null;
        food.image = this.foodImage;
        console.log("got error", error);
      }
    );
    return food; */
  }

  public isQuantityZero(id: number): boolean {
    if (this.quantityForm.get(id+'').value == 0)
      return true;
    else return false;
  }

  public initializeQuantity(id: number): void {
    this.quantityForm.get(id + '').setValue(1);
    console.log('initialize quantity', this.quantityForm);
  }

  trackByFn(index, item) {
    return item.id;
  }

  print(val: any) {
    console.log(val);
  }

}
