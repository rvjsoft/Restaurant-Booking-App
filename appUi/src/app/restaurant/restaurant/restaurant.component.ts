import { Component, OnInit } from '@angular/core';
import { AppServiceService } from 'src/app/app-service.service';
import { DomSanitizer } from '@angular/platform-browser';
import { AddressModel, GetRestaurantsRequest, GetRestaurantResponse, RestaurantModel, FoodModel, RestaurantStatusResponse, RestaurantStatusReqeust } from 'src/app/FoodOrderApp';
import { Status } from 'src/app/AppEnums';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionService } from 'src/app/session.service';
import { from } from 'rxjs';
import { ToastService } from 'src/app/ui-components/toast.service';
import { OrderCheckoutService } from 'src/app/customer/order-checkout.service';

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


  constructor(
    private appService: AppServiceService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private route: ActivatedRoute,
    private session: SessionService,
    private toastService: ToastService,
    private orderService: OrderCheckoutService
    ) { }

  ngOnInit() {
     this.resId = this.route.snapshot.paramMap.get('id');
    let request = new GetRestaurantsRequest();
    if(this.resId != null && this.resId != undefined) {
      request.resId = Number(this.resId);
    }
    this.appService.getRestaurant(request).subscribe(
      (response: GetRestaurantResponse) => {
        let restaurant = response.restaurants[0];
        this.foods = response.foods;
        if (restaurant.address != null)
          this.address = restaurant.address;
        else
          this.address = new AddressModel();
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
      }
    );
  }

  public goToModifyFoods() {
    this.router.navigateByUrl('/restaurant/editfood', {state: {foods: JSON.stringify(this.foods)}});
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
    this.router.navigate(['/restaurant/tables']);
  }

  public uploadImage(files: any) {
    let file = files[0];
    this.appService.uploadImage(file).subscribe(
      (response: any) => {
        this.imageId = response.imageId;
        this.appService.getRestaurantImage(this.imageId).subscribe(
          (imageData) => {
            this.temp = imageData;
            this.image = this.sanitizer.bypassSecurityTrustUrl(imageData);
          }
        );
      },
      (error) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

}
