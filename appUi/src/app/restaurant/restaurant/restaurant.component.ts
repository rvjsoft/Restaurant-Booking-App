import { Component, OnInit } from '@angular/core';
import { AppServiceService } from 'src/app/app-service.service';
import { DomSanitizer } from '@angular/platform-browser';
import { AddressModel, GetRestaurantsRequest, GetRestaurantResponse, RestaurantModel, FoodModel } from 'src/app/FoodOrderApp';
import { Status } from 'src/app/AppEnums';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionService } from 'src/app/session.service';

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


  constructor(
    private appService: AppServiceService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private route: ActivatedRoute,
    private session: SessionService
    ) { }

  ngOnInit() {
    let resId = this.route.snapshot.paramMap.get('id');
    let request = new GetRestaurantsRequest();
    if(resId != null && resId != undefined) {
      request.resId = Number(resId);
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

}
