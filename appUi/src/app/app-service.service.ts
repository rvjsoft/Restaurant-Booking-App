import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginRequest } from './FoodOrderApp';
import { DOCUMENT } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  readonly PATH_LOGIN = 'login';

  constructor(private http: HttpClient, @Inject(DOCUMENT) private document: Document) {}

  public login(request: LoginRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_LOGIN;
    request.messageId = (Date.now() / 1000).toString();
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    console.log(this.document.cookie)
    return this.http.post(requestURL, request, {headers: headers, withCredentials: true});
  }

}
