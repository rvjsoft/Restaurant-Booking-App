import { Component, OnInit } from '@angular/core';
import { AddressModel, AddAddressResponse, DeleteAddressResponse } from 'src/app/FoodOrderApp';
import { ToastService } from '../../ui-components/toast.service';
import { FormBuilder } from '@angular/forms';
import { AppServiceService } from 'src/app/app-service.service';
import { AddressService } from '../address.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-address',
  templateUrl: './add-address.component.html',
  styleUrls: ['./add-address.component.css']
})
export class AddAddressComponent implements OnInit {

  private addresses: AddressModel[];
  private x_mark = '../../../assets/images/x-mark.svg';
  selected = null;
  show_io = false;

  private addressForm = this.formBuilder.group({
    address1: [''],
    address2: [''],
    city: [''],
    landmark: [''],
    state: [''],
    postalCode: [''],
  });

  constructor(
    private toastService: ToastService,
    private formBuilder: FormBuilder,
    private appService: AppServiceService,
    private addressService: AddressService,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location
  ) {
    this.addresses = new Array();
    if (this.addressService.deliveryAddress != null) {
      this.selected = this.addressService.deliveryAddress.id;
    }
  }

  ngOnInit() {
    this.appService.getAddresses().subscribe(
      (response) => {
        this.addresses = response.addresses;
      }
    );
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
    this.appService.addAddress([address]).subscribe(
      (response: AddAddressResponse) => {
        this.toastService.showMessage([response.message], false);
        address.id = response.ids[0];
        this.addresses.push(address);
        this.addressForm.reset();
        this.show_io = false;
      },
      (error: any) => {
        let messages = this.extractErrorMesage(error.error);
        this.toastService.showMessage(messages, true);
      }
    );
  }

  public deleteAddress(address: AddressModel) {
    this.appService.deleteAddress(address.id).subscribe(
      (response: DeleteAddressResponse) => {
        this.toastService.showMessage([response.message], false);
        this.addresses.splice(this.addresses.indexOf(address), 1);
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

  selectAddress(address: AddressModel) {
    if (this.selected == address.id) {
      this.selected = null;
      this.addressService.deliveryAddress = null;
      return;
    }
    this.addressService.deliveryAddress = address;
    this.selected = address.id;
  }

  public closeIO() {
    this.show_io = false;
    this.addressForm.reset();
  }

  public addressSelected() {
    this.location.back();
  }
}
