import { Component, OnInit } from '@angular/core';
import { RestaurantModel, GetRestaurantsRequest, GetRestaurantResponse } from '../../FoodOrderApp';
import { AppServiceService } from 'src/app/app-service.service';
import { Status, FoodType } from 'src/app/AppEnums';
import { ToastService } from 'src/app/ui-components/toast.service';

@Component({
  selector: 'app-search-restaurant',
  templateUrl: './search-restaurant.component.html',
  styleUrls: ['./search-restaurant.component.css']
})
export class SearchRestaurantComponent implements OnInit {

  restaurants: RestaurantModel[];
  status: boolean;
  type: boolean;
  name;

  constructor(private appService: AppServiceService, private toastService: ToastService) { }

  ngOnInit() {
    this.getResList();
  }

  public getResList() {
    let request = new GetRestaurantsRequest();
    request.resName = this.name;
    console.log(this.status);
    if (this.status)
      request.status = Status.AVAILABLE;
    if (this.type)
      request.type = FoodType.VEG;
    console.log(request);
    this.appService.getRestaurant(request, true).subscribe(
      (response: GetRestaurantResponse) => {
        this.restaurants = response.restaurants;
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
