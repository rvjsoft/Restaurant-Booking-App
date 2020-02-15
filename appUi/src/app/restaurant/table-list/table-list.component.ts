import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { TableAvailModel } from 'src/app/FoodOrderApp';

@Component({
  selector: 'app-table-list',
  templateUrl: './table-list.component.html',
  styleUrls: ['./table-list.component.css']
})
export class TableListComponent implements OnInit {

  @Input()
  private availability: Array<{ [date: string]: { [part: string]: TableAvailModel } }>;

  @Input()
  private baseCount: number;

  constructor() { }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges): void {
    for(let data in this.availability){
      if(Object.keys(this.availability[data]).length == 0) {
        this.availability[data] = this.getDummyAvail();
      }
    }
  }

  public selectAvail(eventObj: Event) {
    console.log(eventObj);
  }

  public getDummyAvail(): any {
    let dummyAvail = {
      BREAKFAST: {
        total: -1,
        bookedTables: 0
      },
      LUNCH: {
        total: -1,
        bookedTables: 0
      },
      DINNER: {
        total: -1,
        bookedTables: 0
      }
    }
    return dummyAvail;
  }

}
