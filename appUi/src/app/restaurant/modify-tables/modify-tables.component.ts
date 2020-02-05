import { Component, OnInit } from '@angular/core';
import { PartOfDay } from 'src/app/AppEnums';
import { TableAvailData, RestaurantTableRequest, RestaurantTableResponse } from 'src/app/FoodOrderApp';
import { ToastService } from 'src/app/ui-components/toast.service';
import { AppServiceService } from 'src/app/app-service.service';

@Component({
  selector: 'app-modify-tables',
  templateUrl: './modify-tables.component.html',
  styleUrls: ['./modify-tables.component.css']
})
export class ModifyTablesComponent implements OnInit {

  PartOfDay = Object.keys(PartOfDay);
  tableData = new TableAvailData();

  constructor(private toastService: ToastService, private appService: AppServiceService) { }

  ngOnInit() {
  }

  updateTable() {
    console.log(this.tableData);
    let request = new RestaurantTableRequest();
    request.date = this.tableData.date;
    request.part = this.tableData.part;
    request.tableCount = this.tableData.baseCount;
    this.appService.updateTable(request).subscribe(
      (response: RestaurantTableResponse) => {
        this.toastService.showMessage([response.message], false);
        this.tableData = new TableAvailData();
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
