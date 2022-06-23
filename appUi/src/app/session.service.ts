import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public loadingResPage: boolean = false;

  readonly USER_LEVEL = 'USR_LEVEL';
  readonly USER_NAME = 'USER_NAME';
  readonly USER_PASS = 'USER_PASS';

  get userLevel() {
    let level = sessionStorage.getItem(this.USER_LEVEL);
    if (level != 'CUSTOMER' && level != 'RESTAURANT') {
      return null;
    } else {
      return level;
    }
  }

  set userLevel(val: string) {
    sessionStorage.setItem(this.USER_LEVEL, val);
  }

  get userName() {
    return sessionStorage.getItem(this.USER_NAME);
  }

  set userName(val: string) {
    sessionStorage.setItem(this.USER_NAME, val);
  }

  get password() {
    return sessionStorage.getItem(this.USER_PASS);
  }

  set password(val: string) {
    sessionStorage.setItem(this.USER_PASS, val);
  }

  constructor() { }
}
