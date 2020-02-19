import { Component, OnInit } from '@angular/core';
import { FoodCategory } from 'src/app/AppEnums';
import { FoodModel, AddFoodRequest, AddFoodResponse, UpdateFoodRequest, UpdateFoodResponse, DeleteFoodRequest, DeleteFoodResponse } from 'src/app/FoodOrderApp';
import { ToastService } from 'src/app/ui-components/toast.service';
import { FormBuilder } from '@angular/forms';
import { AppServiceService } from 'src/app/app-service.service';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { of, from } from 'rxjs';
import { filter } from 'rxjs/operators';
import { LoadBarService } from 'src/app/load-bar.service';

@Component({
  selector: 'app-food-edit',
  templateUrl: './food-edit.component.html',
  styleUrls: ['./food-edit.component.css']
})
export class FoodEditComponent implements OnInit {

  categories = Object.keys(FoodCategory);
  foodData: FoodModel = new FoodModel();
  foods: Array<FoodModel>;
  temp: any;
  greyImage = '/assets/images/gray.svg';
  tempImage: any = this.greyImage;

  constructor
    (
      private toastService: ToastService,
      private formBuilder: FormBuilder,
      private appService: AppServiceService,
      private route: ActivatedRoute,
      private router: Router,
      private sanitizer: DomSanitizer,
      private loadBarService: LoadBarService
    ) { }

  ngOnInit() {
    // this.route.queryParamMap.subscribe(
    // (params) => {
    //   console.log(params);
    //   console.log(params.get('foods'));
    // }
    // );
    this.temp = JSON.parse(history.state['foods']);
    this.foods = this.temp;
  }

  public addFood() {
    if(this.foodData.id != null && this.foodData.id != undefined)
    {
      this.updateFood();
      return;
    }
    let request = new AddFoodRequest();
    request.foods = [this.foodData];
    this.appService.addFood(request).subscribe(
      (response: AddFoodResponse) => {
        this.toastService.showMessage([response.message], false);
        this.foodData.id = response.ids[0];
        let food = new FoodModel();
        this.cloneFood(food, this.foodData);
        this.foods = [... this.foods, food];
        this.foods = this.foods;
        this.foodData = new FoodModel();
        this.tempImage = this.greyImage;
      },
      (error: any) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    )
  }

  public updateFood() {
    let request = new UpdateFoodRequest();
    let food = new FoodModel();
    this.cloneFood(food, this.foodData);
    food.id = null;
    request.foodId = this.foodData.id;
    request.food = food;
    this.appService.updateFood(request).subscribe(
      (response: UpdateFoodResponse) => {
        this.toastService.showMessage([response.message], false);
        for(let fd of this.foods) {
          if (fd.id == this.foodData.id){
            this.cloneFood(fd, this.foodData);
            break;
          }
        }
        this.foods = [... this.foods];
        this.foodData = new FoodModel();
      },
      (error: any) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

  public uploadImage(files: any) {
    let file = files[0];
    let id = '-1'; //NO_FOOD_ID
    this.loadBarService.showLoadBar();
    this.appService.uploadImage(file, id).subscribe(
      (response: any) => {
        this.loadBarService.hideLoadBar();
        this.toastService.showMessage([response.message], false);
        this.foodData.imageId = response.imageId;
        this.appService.getRestaurantImage(this.foodData.imageId).subscribe(
          (imageData) => {
            // this.tempImage = this.sanitizer.bypassSecurityTrustUrl(imageData);
            this.tempImage = imageData;
          }
        );
      },
      (error) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
        this.loadBarService.hideLoadBar();
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

  deleteFood(food: FoodModel) {
    let request = new DeleteFoodRequest();
    request.foodId = food.id;
    this.appService.deleteFood(request).subscribe(
      (response: DeleteFoodResponse) => {
        this.toastService.showMessage([response.message], false);
        this.foods.splice(this.foods.indexOf(food), 1);
        this.foods = [... this.foods];
      },
      (error) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

  editFood(food: FoodModel) {
    this.foodData = food;
  }

  private cloneFood(target: FoodModel, source: FoodModel) {
    target.id = source.id;
    target.category = source.category;
    target.image = source.image;
    target.imageId = source.imageId;
    target.name = source.name;
    target.price = source.price;
    target.type = source.type;
  }

  public clear() {
    this.foodData = new FoodModel();
  }

}
