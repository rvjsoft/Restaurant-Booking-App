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
    if (this.session.userLevel == null || undefined) {
      return false;
    } else {
      return true;
    }
  }

  public logout() {
    this.appService.logout().subscribe(
      (response: string) => {
        this.toastService.showMessage([response], false);
        this.router.navigate(['login']);
      }
    );
  }
}
