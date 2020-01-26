import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginRequest, RegisterUserRequest, FileUploadRequest } from './FoodOrderApp';
import { DOCUMENT } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  readonly PATH_LOGIN = 'login';
  readonly PATH_REGISTER = 'register/user';

  constructor(private http: HttpClient, @Inject(DOCUMENT) private document: Document) {}

  public login(request: LoginRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_LOGIN;
    request.messageId = (Date.now() / 1000).toString();
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.post(requestURL, request, {headers: headers, withCredentials: true});
  }

  public createUser(request: RegisterUserRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_REGISTER;
    request.messageId = (Date.now() / 1000).toString();
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.post(requestURL, request, {headers: headers, withCredentials: true});
  }

  public uploadImage(file: File) {
    let formData = new FormData();
    let headers = new HttpHeaders({
      'Content-Type': 'multipart/form-data'
    });
    formData.append('file', file);
    // let request = new FileUploadRequest();
    // request.file = file;
    let requestURL = environment.appURI + 'restaurant/upload';
    this.http.post(requestURL, formData, {headers: {}, withCredentials: true}).subscribe();
  }
}
