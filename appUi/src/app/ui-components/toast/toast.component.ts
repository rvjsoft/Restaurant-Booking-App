import { Component, OnInit } from '@angular/core';
import { ToastService } from '../toast.service';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css']
})
export class ToastComponent implements OnInit {

  show: boolean;
  isError: boolean;
  messages: string[];
  constructor(private toastService: ToastService) {

  }

  ngOnInit() {
    this.toastService.successMessages.subscribe({
       next : (val) => {
         this.messages = val;
         this.show = true;
         this.isError = false;
         window.scrollTo(0, 0);
       }
    });

    this.toastService.errorMessages.subscribe({
      next : (val) => {
        this.messages = val;
        this.show = true;
        this.isError = true;
        window.scrollTo(0, 0);
      }
   });
  }

  private closeMessage() {
    this.show = false;
  }

}
