import { Component } from '@angular/core';
import { SessionService } from './session.service';
import { AppServiceService } from './app-service.service';
import { ToastService } from './ui-components/toast.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'appUi';

  constructor(
    private session: SessionService,
    private appService: AppServiceService,
    private toastService: ToastService,
    private router: Router
    ) {

  }

  get isUserLogged(): boolean {
    console.log(this.session.userLevel == null);
    if (this.session.userLevel == null) {
      return false;
    } else {
      return true;
    }
  }

  public logout() {
    console.log(this.isUserLogged);
    // this.appService.logout().subscribe(
    //   (response: any) => {
        this.session.userLevel = null;
        this.session.userName = null;
        this.session.userName = null;
        this.toastService.showMessage(['Loggedout Successfully.'], false);
        this.router.navigate(['login']);
    //   }
    // );
  }
}
