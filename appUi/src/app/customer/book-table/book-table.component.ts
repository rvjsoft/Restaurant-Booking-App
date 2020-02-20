import { Component, OnInit } from '@angular/core';
import { AppServiceService } from 'src/app/app-service.service';
import { ToastService } from 'src/app/ui-components/toast.service';
import { BookTableService } from '../book-table.service';
import { TableAvailModel, TableAvailData, BookTableRequest, BookTableResponse, TableAvailResponse } from 'src/app/FoodOrderApp';
import { PartOfDay } from 'src/app/AppEnums';
import { LoadBarService } from 'src/app/load-bar.service';

@Component({
  selector: 'app-book-table',
  templateUrl: './book-table.component.html',
  styleUrls: ['./book-table.component.css']
})
export class BookTableComponent implements OnInit {

  availability: { [date: string]: { [part: string]: TableAvailModel } };
  tableData = new TableAvailData();
  PartOfDay = Object.keys(PartOfDay);
  resId: number;
  baseCount: number;

  constructor(
    private appService: AppServiceService,
    private toastService: ToastService,
    private bookTableService: BookTableService,
    public loadBar: LoadBarService
  ) {
    this.availability = this.bookTableService.availability;
    this.resId = this.bookTableService.resId;
    this.baseCount = this.bookTableService.baseCount;
  }

  ngOnInit() {
    // this.populateAvail();
  }

  public bookTable() {
    let request = new BookTableRequest();
    request.date = this.tableData.date;
    request.part = this.tableData.part;
    request.count = this.tableData.baseCount;
    request.resId = this.resId;
    this.loadBar.showLoadBar();
    this.appService.bookTable(request).subscribe(
      (response: BookTableResponse) => {
        this.loadBar.hideLoadBar();
        this.toastService.showMessage([response.message], false);
        this.tableData = new TableAvailData();
        this.populateAvail();
      },
      (error: any) => {
        this.loadBar.hideLoadBar();
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

  public populateAvail() {
    this.appService.getTableAvail(this.resId).subscribe(
      (response: TableAvailResponse) => {
        this.availability = response.availability;
        this.baseCount = response.baseCount;
        console.log(this.availability);
      },
      (error: any) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

  private extractErrorMesage(errorObj: any): string[] {
    let messages: string[] = [];
    if (errorObj == null || errorObj == undefined) {
      messages.push('service not available');
      return messages ;
    }
    messages.push(errorObj.message);
    let errors = errorObj.errors;
    for(let error in errors) {
      messages.push(errors[error]);
    }
    return messages;
  }

}
