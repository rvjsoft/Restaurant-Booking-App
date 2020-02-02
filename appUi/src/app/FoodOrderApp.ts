import { PartOfDay, OrderStatus, Status, FoodType, FoodCategory } from './AppEnums';

// Generated using typescript-generator version 2.0.400 on 2020-01-17 23:29:27.
export class Serializable {
}

export class Comparable<T> {
}

export class InputStreamSource {
  inputStream: any;
}

export class BaseRequest {
  messageId: string;
}

export class BaseResponse {
  messageId: string;
  errors: { [index: string]: string };
  message: string;
}

export class AddAddressRequest extends BaseRequest {
    addresses: AddressModel[];
}

export class AddAddressResponse extends BaseResponse {
  ids: number[];
}

export class AddFoodRequest extends BaseRequest {
    foods: FoodModel[];
}

export class AddFoodResponse extends BaseResponse {
}

export class AddressModel {
    id: number;
    address1: string;
    address2: string;
    landmark: string;
    city: string;
    state: string;
    postalCode: string;
}


export class BookTableRequest extends BaseRequest {
    resId: number;
    date: Date;
    part: PartOfDay;
    count: number;
}

export class BookTableResponse extends BaseResponse {
}

export class CustomerModel {
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
}

export class DeleteAddressRequest extends BaseRequest {
    addressId: number;
}

export class DeleteAddressResponse extends BaseResponse {
}

export class DeleteFoodRequest extends BaseRequest {
    foodId: number;
}

export class DeleteFoodResponse extends BaseResponse {
}

export class FileUploadRequest extends BaseRequest {
    foodId: number;
    file: File;
}

export class FoodModel {
    id: number;
    name: string;
    price: number;
    type: FoodType;
    category: FoodCategory;
    imageId: string;
    image: any;
}

export class FoodStatusRequest extends BaseRequest {
    available: number[];
    unavailable: number[];
}

export class FoodStatusResponse extends BaseResponse {
    errorFoodIds: number[];
}

export class GetOrderRequest extends BaseRequest {
    resId: number;
    custId: number;
    status: OrderStatus;
}

export class GetOrderResponse extends BaseResponse {
    orders: OrderModel[];
}

export class GetAddressRequest extends BaseRequest {

}

export class GetAddressResponse extends BaseResponse {
    addresses: AddressModel[];
}

export class GetRestaurantResponse extends BaseResponse {
    restaurants: RestaurantModel[];
    foods: FoodModel[];
    availability: { [index: string]: { [index: string]: TableAvailModel } };
}

export class GetRestaurantsRequest extends BaseRequest {
    resId: number;
    resName: string;
    type: FoodType;
}

export class GetTableRequest extends BaseRequest {
    resId: number;
    custId: number;
}

export class GetTableResponse extends BaseResponse {
    tableBookings: TableModel[];
}

export class LoginRequest {
    messageId: string;
    userName: string;
    password: string;
}

export class OrderFoodRequest extends BaseRequest {
    resId: number;
    foods: { [index: string]: number };
}

export class OrderFoodResponse extends BaseResponse {
    errorFoodIds: number[];
}

export class OrderItemModel {
    id: number;
    quantity: number;
    food: string;
}

export class OrderModel {
    id: number;
    orderedOn: Date;
    status: OrderStatus;
    customerName: string;
    restaurantName: string;
    foodItems: OrderItemModel[];
}

export class OrderStatusRequest extends BaseRequest {
    orderId: number;
    status: OrderStatus;
}

export class OrderStatusResponse extends BaseResponse {
}

export class RegisterUserRequest extends BaseRequest {
    userName: string;
    password: string;
    restaurant: RestaurantModel;
    customer: CustomerModel;
}

export class RegisterUserResponse extends BaseResponse {
}

export class RestaurantModel {
    name: string;
    type: FoodType;
    address: AddressModel;
    email: string;
    phone: string;
    tableCount: number;
    status: Status;
    imageId: string;
}

export class RestaurantStatusReqeust extends BaseRequest {
    status: Status;
}

export class RestaurantStatusResponse extends BaseResponse {
}

export class RestaurantTableRequest extends BaseRequest {
    tableCount: number;
    date: Date;
    baseCount: number;
    part: PartOfDay;
}

export class RestaurantTableResponse extends BaseResponse {
}

export class TableAvailModel {
    bookedOn: Date;
    total: number;
    bookedTables: number;
}

export class TableAvailRequest extends BaseRequest {
}

export class TableAvailResponse extends BaseResponse {
    availability: { [index: string]: { [index: string]: TableAvailModel } };
}

export class TableModel {
    bookingId: number;
    count: number;
    partOfDay: PartOfDay;
    bookingDate: Date;
    restaurant: string;
    customer: string;
}

export class UpdateAddressRequest extends BaseRequest {
    address: AddressModel;
    addressId: number;
}

export class UpdateAddressResponse extends BaseResponse {
}

export class UpdateFoodRequest extends BaseRequest {
    food: FoodModel;
    foodId: number;
}

export class UpdateFoodResponse extends BaseResponse {
}

export class MultipartFile extends InputStreamSource {
    contentType: string;
    name: string;
    empty: boolean;
    bytes: any;
    resource: Resource;
    size: number;
    originalFilename: string;
}

export class Resource extends InputStreamSource {
    readable: boolean;
    url: URL;
    file: any;
    open: boolean;
    description: string;
    filename: string;
    uri: URI;
}



export class URL extends Serializable {
}

export class URI extends Comparable<URI>{
}
