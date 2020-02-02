import { Component, OnInit } from '@angular/core';
import { FoodCategory } from 'src/app/AppEnums';
import { FoodModel, AddFoodRequest, AddFoodResponse } from 'src/app/FoodOrderApp';
import { ToastService } from 'src/app/ui-components/toast.service';
import { FormBuilder } from '@angular/forms';
import { AppServiceService } from 'src/app/app-service.service';
import { ActivatedRoute, Router, Params } from '@angular/router';

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

  constructor
    (
      private toastService: ToastService,
      private formBuilder: FormBuilder,
      private appService: AppServiceService,
      private route: ActivatedRoute,
      private router: Router
    ) { }

  ngOnInit() {
    // this.route.queryParamMap.subscribe(
    // (params) => {
    //   console.log(params);
    //   console.log(params.get('foods'));
    // }
    // );
    this.temp = this.route.snapshot.paramMap.get('foods');
    this.foods = JSON.parse(this.temp);
  }

  public addFood() {
    let request = new AddFoodRequest();
    request.foods = [this.foodData];
    this.appService.addFood(request).subscribe(
      (response: AddFoodResponse) => {
        this.toastService.showMessage([response.message], false);
        this.foodData = new FoodModel();
      },
      (error: any) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    )
  }

  public uploadImage(files: any) {
    let file = files[0];
    let id = '-1'; //NO_FOOD_ID
    this.appService.uploadImage(file, id).subscribe(
      (response: any) => {
        this.foodData.imageId = response.imageId;
      },
      (error) => {
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
