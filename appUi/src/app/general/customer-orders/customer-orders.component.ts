import { Component, OnInit, HostListener } from '@angular/core';
import { OrderModel, GetOrderRequest, GetOrderResponse } from 'src/app/FoodOrderApp';
import { OrderStatus } from 'src/app/AppEnums';
import { AppServiceService } from 'src/app/app-service.service';
import { ToastService } from 'src/app/ui-components/toast.service';
import { from } from 'rxjs';

@Component({
  selector: 'app-customer-orders',
  templateUrl: './customer-orders.component.html',
  styleUrls: ['./customer-orders.component.css']
})
export class CustomerOrdersComponent implements OnInit {

  orders: Array<OrderModel> = [];
  statusTracker: { [index: string]: OrderStatus } = {};
  newPage = 0;
  newSize = 15;
  orderDate: {[index: string]: string} = {};
  dayMillis = 8.64e+7;

  constructor(private appService: AppServiceService, private toastService: ToastService) { }

  ngOnInit() {
    this.loadOrders();
  }

  public loadOrders() {
    let request = new GetOrderRequest();
    request.page = this.newPage;
    request.size = this.newSize;
    this.getOrders(request);
  }

  public getOrders(request: GetOrderRequest) {
    this.appService.getOrders(request, true).subscribe(
      (response: GetOrderResponse) => {
        from(response.orders).subscribe(
          (order) => {
            this.orders.push(order);
            if (this.statusTracker[order.id + ''] == null || this.statusTracker[order.id + ''] == undefined) {
              this.statusTracker[order.id + ''] = order.status;
            }
            let orderDate = new Date(Date.parse(order.orderedOn + ''));
            if (this.isSameDay(orderDate, new Date(Date.now()))) {
              this.orderDate[order.id + ''] = 'today, ' + this.getTime(orderDate);
              console.log(this.orderDate);
            } else {
              let difference = this.getDifference(new Date(Date.parse(order.orderedOn + '')), new Date(Date.now()));
              this.orderDate[order.id + ''] = difference + ((difference == 1) ? ' day' : ' days') + ' ago';
            }
          }
        );
      },
      (error) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

  public getTime(orderDate: Date) {
    if (orderDate == null || orderDate == undefined)
      return '';
    let minutes = orderDate.getMinutes().toString();
    if (minutes.length == 1) {
      minutes = '0' + minutes;
    }
    return orderDate.getHours() + ":" + minutes;
  }

  public refresh() {
    this.newPage = 0;
    this.orders = [];
    this.loadOrders();
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

  @HostListener('window:scroll', ['$event'])
  public windowScroll(event: any): void {
    console.log(event);
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
      this.newPage++;
      this.loadOrders();
    }
   }

  private isSameDay(date1: Date, date2: Date): boolean {
    if (date1.getDate() == date2.getDate() && date1.getMonth() == date2.getMonth() && date1.getFullYear() == date2.getFullYear()) {
      return true;
    } else {
      return false;
    }
  }

  private getDifference(date1: Date, date2: Date): number {
    let difference = Math.abs(date2.getTime() - date1.getTime());
    return Math.round(difference / this.dayMillis);
  }

}
