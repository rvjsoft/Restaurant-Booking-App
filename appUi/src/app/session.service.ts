import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public loadingResPage: boolean = false;

  constructor() { }
}
