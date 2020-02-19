import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadBarService {

  public loadBarCommand = new Subject<boolean>();

  constructor() { }

  public showLoadBar() {
    this.loadBarCommand.next(true);
  }

  public hideLoadBar() {
    this.loadBarCommand.next(false);
  }
}
