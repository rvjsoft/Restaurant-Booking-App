import { Component, OnInit, HostListener } from '@angular/core';
import { OrderModel, GetOrderRequest, GetOrderResponse, OrderStatusRequest, OrderStatusResponse } from 'src/app/FoodOrderApp';
import { AppServiceService } from 'src/app/app-service.service';
import { ToastService } from 'src/app/ui-components/toast.service';
import { from } from 'rxjs';
import { OrderStatus } from 'src/app/AppEnums';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orders: Array<OrderModel> = [];
  orderByCategory = {};
  foodStatusOption: Array<String>;
  statusTracker: {[index: string]: OrderStatus} = {};
  prevStatus: {[index: string]: OrderStatus} = {};
  newPage = 0;
  newSize = 30;
  scrollData: boolean = false;

  constructor(private appService: AppServiceService, private toastService: ToastService) {
    this.foodStatusOption = Object.keys(OrderStatus);
    for(let status of Object.keys(OrderStatus)) {
      this.orderByCategory[status] = new Array<OrderModel>();
    }
  }

  ngOnInit() {
    this.getNew();
  }

  private extractErrorMesage(errorObj: any): string[] {
    let messages: string[] = [];
    if (errorObj == null || errorObj == undefined) {
      messages.push('service not available');
      return messages;
    }
    messages.push(errorObj.message);
    let errors = errorObj.errors;
    for (let error in errors) {
      messages.push(errors[error]);
    }
    return messages;
  }

  public print() {
    console.log(this.statusTracker);
  }

  public changeOrderStatus(order: OrderModel, status: OrderStatus) {
    let id = order.id;
    let request = new OrderStatusRequest();
    request.orderId = id;
    request.status = status;
    this.appService.changeOrderStatus(request).subscribe(
      (response: OrderStatusResponse) => {
        let statusOld = this.prevStatus[id+''];
        let index = this.orderByCategory[statusOld].indexOf(order);
        this.orderByCategory[statusOld].splice(index, 1);
        this.orderByCategory[status].unshift(order);
        this.prevStatus[id+''] = status;
        let request1 = new GetOrderRequest();
        request1.status = OrderStatus.ORDERED;
        this.loadOrdersData(request1);
        let request2 = new GetOrderRequest();
        request2.status = OrderStatus.ACKNOWLEDGED;
        this.loadOrdersData(request2);
      },
      (error) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
        this.statusTracker[id+''] = this.prevStatus[id+''];
      }
    );
  }

  public getOrders(request: GetOrderRequest) {
    this.appService.getOrders(request).subscribe(
      (response: GetOrderResponse) => {
        from(response.orders).subscribe(
          (order) => {
            this.orderByCategory[request.status].push(order);
            if(this.statusTracker[order.id+''] == null || this.statusTracker[order.id+''] == undefined) {
              this.statusTracker[order.id+''] = order.status;
              this.prevStatus[order.id+''] = order.status;
            }
            console.log(this.statusTracker);
          }
        );
      },
      (error) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

  public getNew() {
    let request = new GetOrderRequest();
    request.status = OrderStatus.ORDERED;
    this.orders = this.orderByCategory[request.status];
    this.loadOrdersData(request);
  }

  public getLive() {
    let request = new GetOrderRequest();
    request.status = OrderStatus.ACKNOWLEDGED;
    this.orders = this.orderByCategory[request.status];
    this.loadOrdersData(request);
  }

  public getHistory() {
    let request = new GetOrderRequest();
    request.status = OrderStatus.DELIVERED;
    this.orders = this.orderByCategory[request.status];
    this.loadOrdersData(request);
  }

  public loadOrdersData(request: GetOrderRequest) {
    if (this.orderByCategory[request.status] != null && this.orderByCategory[request.status] != undefined && this.orderByCategory[request.status].length != 0) {
      console.log(this.orderByCategory[request.status]);
      if (!this.scrollData && this.orderByCategory[request.status].length > 20) {
        return;
      } else {
        request.page = 1;
        request.size = this.orderByCategory[request.status].length;
      }
    } else {
      request.page = this.newPage;
      request.size = this.newSize;
    }
    this.getOrders(request);
  }

  @HostListener('window:scroll', ['$event'])
  public windowScroll(event: any): void {
    console.log(event);
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
      if(this.orders == this.orderByCategory[OrderStatus.DELIVERED]) {
        this.scrollData = true;
        this.getHistory();
        this.scrollData = false;
      }
    }
   }

}
