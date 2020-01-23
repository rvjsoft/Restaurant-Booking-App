import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  public successMessages = new Subject<string[]>();
  public errorMessages = new Subject<string[]>();
  constructor() { }

  public showMessage(message: string[], isError: boolean) {
    if (!isError) {
      this.successMessages.next(message);
    } else {
      this.errorMessages.next(message);
    }
  }
}
