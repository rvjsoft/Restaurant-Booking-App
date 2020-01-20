import { Component, OnInit } from '@angular/core';
import { ToastService } from '../toast.service';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css']
})
export class ToastComponent implements OnInit {

  show: boolean;
  messages: string[];
  constructor(private toastService: ToastService) {

  }

  ngOnInit() {
    this.toastService.toastMessages.subscribe({
       next : (val) => {
         this.messages = val;
         this.show = true;
       }
    });
  }

  private closeMessage() {
    this.show = false;
  }

}
