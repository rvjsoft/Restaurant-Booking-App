import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ToastService } from '../ui-components/toast.service';
import { LoginRequest, BaseResponse } from '../FoodOrderApp';
import { AppServiceService } from '../app-service.service';
import { SessionService } from '../session.service';
import { Router } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { LoadBarService } from '../load-bar.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  readonly USR_LVL_HEADER = 'x-usr-level';

  public loginForm = this.formBuilder.group({
    userName: [''],
    password: ['']
  });

  constructor(
    private toastService: ToastService,
    private formBuilder: FormBuilder,
    private appService: AppServiceService,
    private session: SessionService,
    private router: Router,
    private loadBar: LoadBarService
    ) { }

  ngOnInit() {
  }

  public login() {
    let request : LoginRequest = new LoginRequest();
    request.userName = this.loginForm.get('userName').value;
    request.password = this.loginForm.get('password').value;
    this.loadBar.showLoadBar();
    this.appService.login(request).subscribe(
      (response: HttpResponse<BaseResponse>) => {
        this.loadBar.hideLoadBar();
        let resBody = response.body;
        if(response.headers) {
          this.session.userLevel = response.headers.get(this.USR_LVL_HEADER);
        }
        this.toastService.showMessage([resBody.message], false);
        this.navigateToUserLanding();
      },
      (error: any) => {
        this.loadBar.hideLoadBar();
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
    console.log(this.session.userLevel);
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

  public navigateToUserLanding() {
    let usrLevel = this.session.userLevel;
    console.log(this.session.userLevel);
    if(usrLevel == 'RESTAURANT') {
      this.router.navigate(['res/restaurant']);
    } else if(usrLevel == 'CUSTOMER') {
      this.router.navigate(['customer/home']);
    }
  }

  public goToCustomerCreation() {
    this.router.navigate(['register/customer']);
  }

  public goToRestaurantCreation() {
    this.router.navigate(['register/restaurant']);
  }

}
