import { Component, OnInit } from '@angular/core';
import { ToastService } from '../../ui-components/toast.service';
import { FormBuilder } from '@angular/forms';
import { AppServiceService } from '../../app-service.service';
import { RegisterUserRequest, RegisterUserResponse, CustomerModel } from 'src/app/FoodOrderApp';
import { Router } from '@angular/router';
import { LoadBarService } from 'src/app/load-bar.service';

@Component({
  selector: 'app-customer-registration',
  templateUrl: './customer-registration.component.html',
  styleUrls: ['./customer-registration.component.css']
})
export class CustomerRegistrationComponent implements OnInit {

  public registrationForm = this.formBuilder.group({
    firstName: [''],
    lastName: [''],
    email: [''],
    phone: [''],
    userName: [''],
    password: ['']
  });

  constructor(private toastService: ToastService,
    private formBuilder: FormBuilder,
    private appService: AppServiceService,
    private router: Router,
    public loadBar: LoadBarService
    ) { }

  ngOnInit() {
  }

  public registerCustomer() {
    let request  = new RegisterUserRequest();
    request.userName = this.registrationForm.get('userName').value;
    request.password = this.registrationForm.get('password').value;
    request.customer = new CustomerModel();
    request.customer.firstName = this.registrationForm.get('firstName').value;
    request.customer.lastName = this.registrationForm.get('lastName').value;
    request.customer.email = this.registrationForm.get('email').value;
    request.customer.phone = this.registrationForm.get('phone').value;
    console.log('sending request');
    this.loadBar.showLoadBar();
    this.appService.createUser(request).subscribe(
      (response: RegisterUserResponse) => {
        this.loadBar.hideLoadBar();
        this.toastService.showMessage([response.message], false);
        this.registrationForm.reset();
        this.router.navigate(['/login']);
      },
      (error: any) => {
        this.loadBar.hideLoadBar();
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );

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
}
