import { Component, OnInit } from '@angular/core';
import { PartOfDay } from 'src/app/AppEnums';
import { TableAvailData, RestaurantTableRequest, RestaurantTableResponse, TableAvailRequest, TableAvailResponse, TableAvailModel } from 'src/app/FoodOrderApp';
import { ToastService } from 'src/app/ui-components/toast.service';
import { AppServiceService } from 'src/app/app-service.service';
import { LoadBarService } from 'src/app/load-bar.service';

@Component({
  selector: 'app-modify-tables',
  templateUrl: './modify-tables.component.html',
  styleUrls: ['./modify-tables.component.css']
})
export class ModifyTablesComponent implements OnInit {

  PartOfDay = Object.keys(PartOfDay);
  tableData = new TableAvailData();
  availability: { [date: string]: { [part: string]: TableAvailModel } };
  baseCount: number;
  edit_part = 'Tables';

  constructor(private toastService: ToastService, private appService: AppServiceService, public loadBar: LoadBarService) { }

  ngOnInit() {
    this.populateAvail();

  }

  updateTable() {
    let request = new RestaurantTableRequest();
    request.date = this.tableData.date;
    request.part = this.tableData.part;
    request.tableCount = this.tableData.baseCount;
    this.loadBar.showLoadBar();
    this.appService.updateTable(request).subscribe(
      (response: RestaurantTableResponse) => {
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

  public updateBase() {
    let request = new RestaurantTableRequest();
    request.baseCount = this.baseCount;
    this.loadBar.showLoadBar();
    this.appService.updateTable(request).subscribe(
      (response: RestaurantTableResponse) => {
        this.loadBar.hideLoadBar();
        this.toastService.showMessage([response.message], false);
      },
      (error: any) => {
        this.loadBar.hideLoadBar();
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

  public populateAvail() {
    this.appService.getTableAvail().subscribe(
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
}
