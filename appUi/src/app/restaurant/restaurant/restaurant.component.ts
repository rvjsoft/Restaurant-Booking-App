import { Component, OnInit } from '@angular/core';
import { AppServiceService } from 'src/app/app-service.service';
import { DomSanitizer } from '@angular/platform-browser';
import { AddressModel, GetRestaurantsRequest, GetRestaurantResponse, RestaurantModel, FoodModel, RestaurantStatusResponse, RestaurantStatusReqeust, TableAvailModel } from 'src/app/FoodOrderApp';
import { Status } from 'src/app/AppEnums';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionService } from 'src/app/session.service';
import { from } from 'rxjs';
import { ToastService } from 'src/app/ui-components/toast.service';
import { OrderCheckoutService } from 'src/app/customer/order-checkout.service';
import { BookTableService } from 'src/app/customer/book-table.service';
import { LoadBarService } from 'src/app/load-bar.service';

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.css']
})
export class RestaurantComponent implements OnInit {

  name;
  imageId;
  type;
  image: any;
  temp: any = '/assets/images/res_image.svg';
  imageVeg = '/assets/images/veg.svg';
  imageNonVeg = '/assets/images/non_veg.svg';
  address: AddressModel = new AddressModel();
  isAvailable: Status;
  baseCount;
  foods: Array<FoodModel>;
  resId;
  email;
  phone;
  availability: { [date: string]: { [part: string]: TableAvailModel } };


  constructor(
    private appService: AppServiceService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private route: ActivatedRoute,
    public session: SessionService,
    private toastService: ToastService,
    private orderService: OrderCheckoutService,
    private bookTableService: BookTableService,
    private loadBarService: LoadBarService
    ) { }

  ngOnInit() {
     this.resId = this.route.snapshot.paramMap.get('id');
    let request = new GetRestaurantsRequest();
    if(this.resId != null && this.resId != undefined) {
      request.resId = Number(this.resId);
    }
    this.loadBarService.showLoadBar();
    this.appService.getRestaurant(request).subscribe(
      (response: GetRestaurantResponse) => {
        this.loadBarService.hideLoadBar();
        let restaurant = response.restaurants[0];
        this.foods = response.foods;
        if (restaurant.address != null)
          this.address = restaurant.address;
        else
          this.address = new AddressModel();
        this.availability = response.availability;
        this.baseCount = restaurant.tableCount;
        this.isAvailable = restaurant.status;
        this.type = restaurant.type;
        this.name = restaurant.name;
        this.email = restaurant.email;
        this.phone = restaurant.phone;
        this.imageId = restaurant.imageId;
        if (this.imageId != null && this.imageId != undefined) {
          this.appService.getRestaurantImage(this.imageId).subscribe(
            (imageData) => {
              this.temp = imageData;
              this.image = this.sanitizer.bypassSecurityTrustUrl(imageData);
              console.log("got data");
            }, (error) => {
              this.image = "";
              console.log("got error", error);
            }
          );
        }
      },
      (error: any) => {
        this.loadBarService.hideLoadBar();
      }
    );
  }

  public goToModifyFoods() {
    this.router.navigateByUrl('/res/editfood', {state: {foods: JSON.stringify(this.foods)}});
  }

  public checkout(event: any) {
    this.orderService.foods = event['foods'];
    this.orderService.quantity = event['quantity'];
    this.orderService.resId = this.resId;
    this.router.navigateByUrl('/customer/checkout', {state: {foods: JSON.stringify(event['foods']), quantity: JSON.stringify(event['quantity']), resId: this.resId}});
  }

  get isRes() {
    if (this.session.userLevel == 'RESTAURANT')
      return true;
    else
      return false;
  }

  public changeStatus() {
    let request = new RestaurantStatusReqeust();
    request.status = this.isAvailable;
    this.appService.changeStatus(request).subscribe(
      (response: RestaurantStatusResponse) => {
        this.toastService.showMessage([response.message], false);
      }, (error) => {
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

  public goToTables() {
    this.router.navigate(['/res/tables']);
  }

  public goToBookTable() {
    this.bookTableService.availability = this.availability;
    this.bookTableService.resId = this.resId;
    this.bookTableService.baseCount = this.baseCount;
    this.router.navigate(['/customer/booktable']);
  }

  public uploadImage(files: any) {
    let file = files[0];
    this.loadBarService.showLoadBar();
    this.appService.uploadImage(file).subscribe(
      (response: any) => {
        this.loadBarService.hideLoadBar();
        this.toastService.showMessage([response.message], false);
        this.imageId = response.imageId;
        this.appService.getRestaurantImage(this.imageId).subscribe(
          (imageData) => {
            this.temp = imageData;
            this.image = this.sanitizer.bypassSecurityTrustUrl(imageData);
          }
        );
      },
      (error) => {
        this.loadBarService.hideLoadBar();
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

}
