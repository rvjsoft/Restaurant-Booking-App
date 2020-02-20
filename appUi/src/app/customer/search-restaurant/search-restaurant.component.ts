import { Component, OnInit } from '@angular/core';
import { RestaurantModel, GetRestaurantsRequest, GetRestaurantResponse } from '../../FoodOrderApp';
import { AppServiceService } from 'src/app/app-service.service';
import { Status, FoodType } from 'src/app/AppEnums';
import { ToastService } from 'src/app/ui-components/toast.service';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/session.service';
import { LoadBarService } from 'src/app/load-bar.service';

@Component({
  selector: 'app-search-restaurant',
  templateUrl: './search-restaurant.component.html',
  styleUrls: ['./search-restaurant.component.css']
})
export class SearchRestaurantComponent implements OnInit {

  restaurants: RestaurantModel[] = [];
  status: boolean;
  type: boolean;
  name;
  private size;
  private page;

  constructor(
    private appService: AppServiceService,
    private toastService: ToastService,
    private router: Router,
    private session: SessionService,
    public loadBar: LoadBarService
  ) { }

  ngOnInit() {
    this.resetPage();
    this.getResList();
  }

  public loadNextPage() {
    this.page++;
    if (!this.session.loadingResPage) {
      this.getResList();
    }
  }

  public resetPage() {
    this.page = 0;
    this.size = 10;
    this.restaurants = [];
  }

  public getResList() {
    let request = new GetRestaurantsRequest();
    request.page = this.page;
    request.size = this.size;
    request.resName = this.name;
    if (this.status)
      request.status = Status.AVAILABLE;
    if (this.type)
      request.type = FoodType.VEG;
    this.session.loadingResPage = true;
    this.loadBar.showLoadBar();
    this.appService.getRestaurant(request, true).subscribe(
      (response: GetRestaurantResponse) => {
        // this.restaurants = response.restaurants;
        this.restaurants = this.restaurants.concat(response.restaurants);
        this.session.loadingResPage = false;
        this.loadBar.hideLoadBar();
      },
      (error) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
        this.session.loadingResPage = false;
        this.loadBar.hideLoadBar();
      }
    );
  }

  public selectRes(res: RestaurantModel) {
    this.router.navigate(['/customer/restaurant', {id: res.id}]);
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
