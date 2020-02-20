import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadBarService {

  public loadBarCommand = new Subject<boolean>();
  public loading: boolean;
  constructor() { }

  public showLoadBar() {
    this.loading = true;
    this.loadBarCommand.next(true);
  }

  public hideLoadBar() {
    this.loading = false;
    this.loadBarCommand.next(false);
  }
}
