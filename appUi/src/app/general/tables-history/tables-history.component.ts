import { Component, OnInit, HostListener } from '@angular/core';
import { ToastService } from 'src/app/ui-components/toast.service';
import { AppServiceService } from 'src/app/app-service.service';
import { GetTableRequest, GetTableResponse, TableModel } from 'src/app/FoodOrderApp';
import { from } from 'rxjs';
import { SessionService } from 'src/app/session.service';
import { LoadBarService } from 'src/app/load-bar.service';

@Component({
  selector: 'app-tables-history',
  templateUrl: './tables-history.component.html',
  styleUrls: ['./tables-history.component.css']
})
export class TablesHistoryComponent implements OnInit {

  page = 0;
  size = 15;
  dayMillis = 8.64e+7;
  readonly TODAY = '01';
  readonly HISTORY = '02';
  todayBookings: Array<TableModel> = [];
  historyBookings: Array<TableModel> = [];
  tableByCategory = {};
  res: boolean;
  heading = {};

  constructor(private toastService: ToastService, private appService: AppServiceService, private sessionService: SessionService, public loadBar: LoadBarService) {
    this.tableByCategory[this.TODAY] = this.todayBookings;
    this.tableByCategory[this.HISTORY] = this.historyBookings;
    this.heading[this.TODAY] = 'Upcoming';
    this.heading[this.HISTORY] = 'History';
  }

  ngOnInit() {
    this.res = this.isRes;
    this.loadTables();
  }

  public loadTables() {
    let request = new GetTableRequest();
    request.page = this.page;
    request.size = this.size;
    this.getTables(request);
  }

  public refresh() {
    this.page = 0;
    this.todayBookings = [];
    this.historyBookings = [];
    this.loadTables();
  }

  public getTables(request: GetTableRequest) {
    this.loadBar.showLoadBar();
    this.appService.getTables(request, !this.res).subscribe(
      (response: GetTableResponse) => {
        this.loadBar.hideLoadBar();
        from(response.tableBookings).subscribe(
          (booking) => {
            let orderDate = new Date(Date.parse(booking.bookingDate + ''));
            if (this.isSameDayOrFutre(orderDate, new Date(Date.now()))) {
              this.todayBookings.push(booking);
            } else {
              this.historyBookings.push(booking);
            }
            console.log(this.tableByCategory);
          }
        );
      },
      (error) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

  @HostListener('window:scroll', ['$event'])
  public windowScroll(event: any): void {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
      this.page++;
      this.loadTables();
    }
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

  private isSameDayOrFutre(date1: Date, date2: Date): boolean {
    if (date1.getDate() == date2.getDate() && date1.getMonth() == date2.getMonth() && date1.getFullYear() == date2.getFullYear()) {
      return true;
    } else if (date1.getTime() > date2.getTime()) {
      return true;
    } else {
      return false;
    }
  }

  private getDifference(date1: Date, date2: Date): number {
    let difference = Math.abs(date2.getTime() - date1.getTime());
    return Math.round(difference / this.dayMillis);
  }

  get isRes(): boolean {
    if (this.sessionService.userLevel == 'RESTAURANT')
      return true;
    else
      return false;
  }

}
