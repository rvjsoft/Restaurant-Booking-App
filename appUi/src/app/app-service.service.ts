import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginRequest, RegisterUserRequest, AddressModel, AddAddressRequest, DeleteAddressRequest, GetRestaurantsRequest, AddFoodRequest, UpdateFoodRequest, DeleteFoodRequest, RestaurantTableRequest, TableAvailRequest, OrderFoodRequest, FoodStatusRequest, RestaurantStatusReqeust, GetOrderRequest, OrderStatusRequest, GetTableRequest, BookTableRequest } from './FoodOrderApp';
import { ImageResponseCacheService } from './image-response-cache.service';

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  readonly RES_SINGLE = 'single';
  readonly RES_LIST = 'list';

  readonly PATH_LOGIN = 'login';
  readonly PATH_REGISTER = 'register/user';
  readonly PATH_ADD_ADDRESS = 'customer/add/address';
  readonly PATH_GET_ADDRESS = 'customer/get/address';
  readonly PATH_DELETE_ADDRESS = 'customer/delete/address';
  readonly PATH_GET_RESTAURANT = 'gen/get/restaurant';
  readonly PATH_GET_RES_IMAGE = 'gen/get/image/';
  readonly PATH_ADD_FOOD = 'restaurant/add/food';
  readonly PATH_UPDATE_FOOD = 'restaurant/update/food';
  readonly PATH_FOOD_STATUS = 'restaurant/status/food';
  readonly PATH_RES_STATUS = 'restaurant/status';
  readonly PATH_DELETE_FOOD = 'restaurant/delete/food';
  readonly PATH_TABLE_COUNT = 'restaurant/tablecount';
  readonly PATH_TABLE_AVAIL = 'gen/get/tablesAvail';
  readonly PATH_GET_RES_LIST = 'customer/get/restlist';
  readonly PATH_ORDER_FOOD = 'customer/order';
  readonly PATH_RES_GET_ORDERS = 'restaurant/get/orders';
  readonly PATH_CUST_GET_ORDERS = 'customer/get/orders';
  readonly PATH_RES_ORDER_STATUS = 'restaurant/orderstatus';
  readonly PATH_RES_GET_TABLE = 'restaurant/get/tables';
  readonly PATH_CUST_GET_TABLE = 'customer/get/tables';
  readonly PATH_BOOK_TABLE = 'customer/booktable';
  readonly PATH_LOGOUT = 'logout';

  constructor(private http: HttpClient, private imageCache: ImageResponseCacheService) {}

  public login(request: LoginRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_LOGIN;
    request.messageId = (Date.now() / 1000).toString();
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa(request.userName + ':' + request.password)
    });
    return this.http.post(requestURL, request, {observe: 'response', headers: headers, withCredentials: true});
  }

  public logout(): Observable<any> {
    let requestURL = environment.appURI + this.PATH_LOGOUT;
    return this.http.post(requestURL, null, {withCredentials: true});
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

  public orderFood(request: OrderFoodRequest): Observable<any>{
    let requestURL = environment.appURI + this.PATH_ORDER_FOOD;
    request.messageId = (Date.now() / 1000).toString();
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }

  public getRestaurant(request: GetRestaurantsRequest, isList?: boolean): Observable<any> {
    let requestURL = environment.appURI + (isList ? this.PATH_GET_RES_LIST : this.PATH_GET_RESTAURANT);
    let params = new HttpParams();
    params = params.set('messageId', (Date.now() / 1000).toString());
    if (request.resId != null && request.resId != undefined)
      params = params.set('resId', request.resId + '');
    if (request.resName != null && request.resName != undefined)
      params = params.set('resName', request.resName);
    if (request.type != null && request.type != undefined)
      params = params.set('type', request.type);
    if (request.status != null && request.status != undefined)
      params = params.set('status', request.status);
    if (request.page != null && request.page != undefined)
      params = params.set('page', request.page + '');
    if (request.size != null && request.size != undefined)
      params = params.set('size', request.size + '');
    return this.http.get(requestURL, { headers: {}, params: params, withCredentials: true });
  }

  public addFood(request: AddFoodRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_ADD_FOOD;
    request.messageId = (Date.now() / 1000).toString();
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }

  public updateFood(request: UpdateFoodRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_UPDATE_FOOD;
    request.messageId = (Date.now() / 1000).toString();
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }

  public deleteFood(request: DeleteFoodRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_DELETE_FOOD;
    request.messageId = (Date.now() / 1000).toString();
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }

  public updateFoodStatus(request: FoodStatusRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_FOOD_STATUS;
    request.messageId = (Date.now() / 1000).toString();
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }

  public changeStatus(request: RestaurantStatusReqeust): Observable<any> {
    let requestURL = environment.appURI + this.PATH_RES_STATUS;
    request.messageId = (Date.now() / 1000).toString();
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }

  public updateTable(request: RestaurantTableRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_TABLE_COUNT;
    request.messageId = (Date.now() / 1000).toString();
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }

  public bookTable(request: BookTableRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_BOOK_TABLE;
    request.messageId = (Date.now() / 1000).toString();
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }

  public getTableAvail(resId?: number): Observable<any> {
    let requestURL = environment.appURI + this.PATH_TABLE_AVAIL;
    let params = new HttpParams().set("messageId", (Date.now() / 1000).toString());
    if(resId != null) {
      params = params.set('resId', resId + '');
    }
    return this.http.get(requestURL, {params: params, headers: {}, withCredentials: true});
  }

  public getRestaurantImage(imageId: string): Observable<any> {
    let requestURL = environment.appURI + this.PATH_GET_RES_IMAGE + imageId;
    return this.http.get(requestURL, {headers: {}, responseType: 'text', withCredentials: true});
  }

  public uploadImage(file: File, foodId?: string): Observable<any> {
    let formData = new FormData();
    formData.append('file', file);
    let params = new HttpParams();
    if (foodId != null && foodId != undefined)
      params = params.set('foodId', foodId);
    let requestURL = environment.appURI + 'restaurant/upload';
    return this.http.post(requestURL, formData, { headers: {}, params: params, withCredentials: true });
  }

  public getOrders(request: GetOrderRequest, isCustomer?: boolean): Observable<any> {
    let requestURL = environment.appURI;
    if (isCustomer === true) {
      requestURL += this.PATH_CUST_GET_ORDERS;
    } else {
      requestURL += this.PATH_RES_GET_ORDERS;
    }
    let params = new HttpParams();
    params = params.set('messageId', (Date.now() / 1000).toString());
    if (request.custId != null && request.custId != undefined)
      params = params.set('custId', request.custId + '');
    if (request.resId != null && request.resId != undefined)
      params = params.set('resId', request.resId + '');
    if (request.status != null && request.status != undefined)
      params = params.set('status', request.status + '');
    if (request.page != null && request.page != undefined)
      params = params.set('page', request.page + '');
    if (request.size != null && request.size != undefined)
      params = params.set('size', request.size + '');

    return this.http.get(requestURL, { headers: {}, params: params, withCredentials: true });
  }

  public getTables(request: GetTableRequest, isCustomer?: boolean): Observable<any> {
    let requestURL = environment.appURI;
    if (isCustomer === true) {
      requestURL += this.PATH_CUST_GET_TABLE;
    } else {
      requestURL += this.PATH_RES_GET_TABLE;
    }
    let params = new HttpParams();
    params = params.set('messageId', (Date.now() / 1000).toString());
    if (request.custId != null && request.custId != undefined)
      params = params.set('custId', request.custId + '');
    if (request.resId != null && request.resId != undefined)
      params = params.set('resId', request.resId + '');
    if (request.page != null && request.page != undefined)
      params = params.set('page', request.page + '');
    if (request.size != null && request.size != undefined)
      params = params.set('size', request.size + '');

    return this.http.get(requestURL, { headers: {}, params: params, withCredentials: true });
  }

  public changeOrderStatus(request: OrderStatusRequest): Observable<any> {
    let requestURL = environment.appURI + this.PATH_RES_ORDER_STATUS;
    request.messageId = (Date.now() / 1000).toString();
    return this.http.post(requestURL, request, {headers: {}, withCredentials: true});
  }
}
