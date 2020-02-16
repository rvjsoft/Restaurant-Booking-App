import { Component, OnInit } from '@angular/core';
import { ToastService } from '../../ui-components/toast.service';
import { FormBuilder } from '@angular/forms';
import { AppServiceService } from '../../app-service.service';
import { RegisterUserRequest, RegisterUserResponse, CustomerModel, RestaurantModel, AddressModel } from 'src/app/FoodOrderApp';
import { FoodType } from '../../AppEnums';
import { Router } from '@angular/router';


@Component({
  selector: 'app-restaurant-registration',
  templateUrl: './restaurant-registration.component.html',
  styleUrls: ['./restaurant-registration.component.css']
})
export class RestaurantRegistrationComponent implements OnInit {

  private image;
  private registrationForm = this.formBuilder.group({
    resName: [''],
    type: [''],
    email: [''],
    phone: [''],
    tableCount: [''],
    userName: [''],
    password: [''],
    address: this.formBuilder.group({
      address1: [''],
      address2: [''],
      city: [''],
      landmark: [''],
      state: [''],
      postalCode: [''],
    }),
    image: ['']
  });

  constructor(private toastService: ToastService,
    private formBuilder: FormBuilder,
    private appService: AppServiceService,
    private router: Router) { }

  ngOnInit() {
  }

  public registerRestaurant() {
    let request  = new RegisterUserRequest();
    request.userName = this.registrationForm.get('userName').value;
    request.password = this.registrationForm.get('password').value;
    let restaurant = new RestaurantModel();
    restaurant.name = this.registrationForm.get('resName').value;
    restaurant.tableCount = this.registrationForm.get('tableCount').value;
    let type = this.registrationForm.get('type').value;
    if (type) {
      restaurant.type = FoodType.VEG;
    } else {
      restaurant.type = FoodType.NON_VEG;
    }
    restaurant.email = this.registrationForm.get('email').value;
    restaurant.phone = this.registrationForm.get('phone').value;
    let address = new AddressModel;
    address.address1 = this.registrationForm.get('address.address1').value;
    address.address2 = this.registrationForm.get('address.address2').value;
    address.city = this.registrationForm.get('address.city').value;
    address.landmark = this.registrationForm.get('address.landmark').value;
    address.state = this.registrationForm.get('address.state').value;
    address.postalCode = this.registrationForm.get('address.postalCode').value;
    restaurant.address = address;
    request.restaurant = restaurant;
    console.log('sending request');
    this.appService.createUser(request).subscribe(
      (response: RegisterUserResponse) => {
        this.toastService.showMessage([response.message], false);
        this.registrationForm.reset();
        this.router.navigate(['/login']);
      },
      (error: any) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
    console.log(this.registrationForm.get('image').value);
    this.image = 'file:///' + this.registrationForm.get('image').value;
  }

  private extractErrorMesage(errorObj: any): string[] {
    let messages: string[] = [];
    if (errorObj == null || errorObj == undefined) {
      messages.push('service not available');
      return messages ;
    }
    messages.push(errorObj.message);
    let errors = errorObj.errors;
    for(let error in errors) {
      messages.push(errors[error]);
    }

    return messages;
  }

  /* public temp(val: any) {
    console.log(val);
    this.appService.uploadImage(val.target.files.item(0));
  } */

}
