import { Component, OnInit } from '@angular/core';
import { AddressModel, AddAddressResponse } from 'src/app/FoodOrderApp';
import { ToastService } from '../../ui-components/toast.service';
import { FormBuilder } from '@angular/forms';
import { AppServiceService } from 'src/app/app-service.service';

@Component({
  selector: 'app-add-address',
  templateUrl: './add-address.component.html',
  styleUrls: ['./add-address.component.css']
})
export class AddAddressComponent implements OnInit {

  private addresses: AddressModel[];

  private addressForm = this.formBuilder.group({
    address1: [''],
    address2: [''],
    city: [''],
    landmark: [''],
    state: [''],
    postalCode: [''],
  });

  constructor(private toastService: ToastService, private formBuilder: FormBuilder, private appService: AppServiceService) {
    this.addresses = new Array();
  }

  ngOnInit() {
  }

  public addAddress() {
    if (this.addresses.length > 3)
      return;
    let address = new AddressModel;
    address.address1 = this.addressForm.get('address1').value;
    address.address2 = this.addressForm.get('address2').value;
    address.city = this.addressForm.get('city').value;
    address.landmark = this.addressForm.get('landmark').value;
    address.state = this.addressForm.get('state').value;
    address.postalCode = this.addressForm.get('postalCode').value;
    this.addresses.push(address);
    this.appService.addAddress(this.addresses).subscribe(
      (response: AddAddressResponse) => {
        this.toastService.showMessage([response.message], false);
        this.addressForm.reset();
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
