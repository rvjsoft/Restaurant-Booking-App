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
          }
        );
      },
      (error) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
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

}
