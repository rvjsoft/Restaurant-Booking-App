import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginRequest, RegisterUserRequest, FileUploadRequest, AddressModel, AddAddressRequest, GetAddressRequest, DeleteAddressRequest } from './FoodOrderApp';
import { DOCUMENT } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  readonly PATH_LOGIN = 'login';
  readonly PATH_REGISTER = 'register/user';
  readonly PATH_ADD_ADDRESS = 'customer/add/address';
  readonly PATH_GET_ADDRESS = 'customer/get/address';
  readonly PATH_DELETE_ADDRESS = 'customer/delete/address';

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

  public addAddress(addresses: AddressModel[]): Observable<any>{
    let requestURL = environment.appURI + this.PATH_ADD_ADDRESS;
    let request = new AddAddressRequest();
    request.messageId = (Date.now() / 1000).toString();
    request.addresses = addresses;
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }

  public getAddresses(): Observable<any>{
    let requestURL = environment.appURI + this.PATH_GET_ADDRESS;
    let params = new HttpParams();
    params = params.set("messageId", (Date.now() / 1000).toString());
    console.log(params);
    return this.http.get(requestURL,{params: params, withCredentials: true});
  }

  public deleteAddress(id: number): Observable<any>{
    let requestURL = environment.appURI + this.PATH_DELETE_ADDRESS;
    let request = new DeleteAddressRequest();
    request.messageId = (Date.now() / 1000).toString();
    request.addressId = id;
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }

  public uploadImage(file: File): Observable<any> {
    let formData = new FormData();
    formData.append('file', file);
    let requestURL = environment.appURI + 'restaurant/upload';
    return this.http.post(requestURL, formData, {headers: {}, withCredentials: true});
  }
}
