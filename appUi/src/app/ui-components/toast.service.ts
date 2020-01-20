import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  public toastMessages = new Subject<string[]>();
  constructor() { }

  public showMessage(message: string[]) {
    this.toastMessages.next(message);
  }
}
