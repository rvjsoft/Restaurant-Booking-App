import { Component, OnInit } from '@angular/core';
import { LoadBarService } from '../load-bar.service';

@Component({
  selector: 'app-load-bar',
  templateUrl: './load-bar.component.html',
  styleUrls: ['./load-bar.component.css']
})
export class LoadBarComponent implements OnInit {

  show: boolean;
  constructor(private loadBarService: LoadBarService) { }

  ngOnInit() {
    this.loadBarService.loadBarCommand.subscribe(
      (command: boolean) => {
        console.log(command);
        this.show = command;
      }
    );
  }

}
