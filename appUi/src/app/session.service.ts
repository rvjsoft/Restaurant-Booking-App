import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public loadingResPage: boolean = false;

  readonly USER_LEVEL = 'USR_LEVEL';

  get userLevel() {
    let level = sessionStorage.getItem(this.USER_LEVEL);
    return level;
  }

  set userLevel(val: string) {
    sessionStorage.setItem(this.USER_LEVEL, val);
  }

  constructor() { }
}
