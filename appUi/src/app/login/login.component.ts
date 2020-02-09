import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ToastService } from '../ui-components/toast.service';
import { LoginRequest, BaseResponse } from '../FoodOrderApp';
import { AppServiceService } from '../app-service.service';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private loginForm = this.formBuilder.group({
    userName: [''],
    password: ['']
  });

  constructor(private toastService: ToastService, private formBuilder: FormBuilder, private appService: AppServiceService, private session: SessionService) { }

  ngOnInit() {
  }

  public login() {
    let request : LoginRequest = new LoginRequest();
    request.userName = this.loginForm.get('userName').value;
    request.password = this.loginForm.get('password').value;
    this.appService.login(request).subscribe(
      (response: BaseResponse) => {
        console.log(response);
        this.toastService.showMessage([response.message], false);
      },
      (error: any) => {
        console.log(error);
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

}
