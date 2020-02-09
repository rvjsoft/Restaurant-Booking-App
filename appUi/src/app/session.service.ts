import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public loadingResPage: boolean = false;

  readonly USER_LEVEL = 'USR_LEVEL';

  get userLevel() {
    let cookies = document.cookie.split('; ');
    for(let val in cookies) {
      let cookie = cookies[val];
      if(cookie.indexOf(this.USER_LEVEL) == 0) {
        return cookie.substring(this.USER_LEVEL.length+1, cookie.length);
      }
    }
    return null;
  }

  constructor() { }
}
