// Generated using typescript-generator version 2.0.400 on 2020-01-17 23:29:27.

export interface AddAddressRequest extends BaseRequest {
    addresses: AddressModel[];
}

export interface AddAddressResponse extends BaseResponse {
}

export interface AddFoodRequest extends BaseRequest {
    foods: FoodModel[];
}

export interface AddFoodResponse extends BaseResponse {
}

export interface AddressModel {
    address1: string;
    address2: string;
    landmark: string;
    city: string;
    state: string;
}

export interface BaseRequest {
    messageId: string;
}

export interface BaseResponse {
    messageId: string;
    errors: { [index: string]: string };
    message: string;
}

export interface BookTableRequest extends BaseRequest {
    resId: number;
    date: Date;
    part: PartOfDay;
    count: number;
}

export interface BookTableResponse extends BaseResponse {
}

export interface CustomerModel {
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
}

export interface DeleteAddressRequest extends BaseRequest {
    addressId: number;
}

export interface DeleteAddressResponse extends BaseResponse {
}

export interface DeleteFoodRequest extends BaseRequest {
    foodId: number;
}

export interface DeleteFoodResponse extends BaseResponse {
}

export interface FileUploadRequest extends BaseRequest {
    foodId: number;
    file: MultipartFile;
}

export interface FoodModel {
    id: number;
    name: string;
    price: number;
    type: FoodType;
    category: FoodCategory;
    imageId: string;
}

export interface FoodStatusRequest extends BaseRequest {
    available: number[];
    unavailable: number[];
}

export interface FoodStatusResponse extends BaseResponse {
    errorFoodIds: number[];
}

export interface GetOrderRequest extends BaseRequest {
    resId: number;
    custId: number;
    status: OrderStatus;
}

export interface GetOrderResponse extends BaseResponse {
    orders: OrderModel[];
}

export interface GetRestaurantResponse extends BaseResponse {
    restaurants: RestaurantModel[];
    foods: FoodModel[];
    availability: { [index: string]: { [index: string]: TableAvailModel } };
}

export interface GetRestaurantsRequest extends BaseRequest {
    resId: number;
    resName: string;
    type: FoodType;
}

export interface GetTableRequest extends BaseRequest {
    resId: number;
    custId: number;
}

export interface GetTableResponse extends BaseResponse {
    tableBookings: TableModel[];
}

export interface LoginRequest {
    messageId: string;
    userName: string;
    password: string;
}

export interface OrderFoodRequest extends BaseRequest {
    resId: number;
    foods: { [index: string]: number };
}

export interface OrderFoodResponse extends BaseResponse {
    errorFoodIds: number[];
}

export interface OrderItemModel {
    id: number;
    quantity: number;
    food: string;
}

export interface OrderModel {
    id: number;
    orderedOn: Date;
    status: OrderStatus;
    customerName: string;
    restaurantName: string;
    foodItems: OrderItemModel[];
}

export interface OrderStatusRequest extends BaseRequest {
    orderId: number;
    status: OrderStatus;
}

export interface OrderStatusResponse extends BaseResponse {
}

export interface RegisterUserRequest extends BaseRequest {
    userName: string;
    password: string;
    restaurant: RestaurantModel;
    customer: CustomerModel;
}

export interface RegisterUserResponse extends BaseResponse {
}

export interface RestaurantModel {
    name: string;
    type: FoodType;
    address: AddressModel;
    email: string;
    phone: string;
    tableCount: number;
    status: Status;
    imageId: string;
}

export interface RestaurantStatusReqeust extends BaseRequest {
    status: Status;
}

export interface RestaurantStatusResponse extends BaseResponse {
}

export interface RestaurantTableRequest extends BaseRequest {
    tableCount: number;
    date: Date;
    baseCount: number;
    part: PartOfDay;
}

export interface RestaurantTableResponse extends BaseResponse {
}

export interface TableAvailModel {
    bookedOn: Date;
    total: number;
    bookedTables: number;
}

export interface TableAvailRequest extends BaseRequest {
}

export interface TableAvailResponse extends BaseResponse {
    availability: { [index: string]: { [index: string]: TableAvailModel } };
}

export interface TableModel {
    bookingId: number;
    count: number;
    partOfDay: PartOfDay;
    bookingDate: Date;
    restaurant: string;
    customer: string;
}

export interface UpdateAddressRequest extends BaseRequest {
    address: AddressModel;
    addressId: number;
}

export interface UpdateAddressResponse extends BaseResponse {
}

export interface UpdateFoodRequest extends BaseRequest {
    food: FoodModel;
    foodId: number;
}

export interface UpdateFoodResponse extends BaseResponse {
}

export interface MultipartFile extends InputStreamSource {
    contentType: string;
    name: string;
    empty: boolean;
    bytes: any;
    resource: Resource;
    size: number;
    originalFilename: string;
}

export interface Resource extends InputStreamSource {
    readable: boolean;
    url: URL;
    file: any;
    open: boolean;
    description: string;
    filename: string;
    uri: URI;
}

export interface InputStreamSource {
    inputStream: any;
}

export interface URL extends Serializable {
}

export interface URI extends Comparable<URI>, Serializable {
}

export interface Serializable {
}

export interface Comparable<T> {
}

export type PartOfDay = "BREAKFAST" | "LUNCH" | "DINNER";

export type FoodType = "VEG" | "NON_VEG";

export type FoodCategory = "STARTERS" | "RICE" | "BIRIYANI" | "BREADS" | "DOSA_IDLY" | "JUICES" | "DESERTS" | "CAKES" | "GRAVY" | "CURRY" | "SNACKS";

export type OrderStatus = "ORDERED" | "ACKNOWLEDGED" | "PREPARING" | "DELIVERED";

export type Status = "AVAILABLE" | "UNAVAILABLE";
