import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastComponent } from './toast/toast.component';
import { ToastService } from './toast.service';



@NgModule({
  declarations: [ToastComponent],
  imports: [
    CommonModule
  ],
  exports: [
    ToastComponent
  ]
})
export class UiComponentsModule { }
