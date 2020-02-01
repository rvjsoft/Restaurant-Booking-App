import { Component, OnInit } from '@angular/core';
import { AppServiceService } from 'src/app/app-service.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.css']
})
export class RestaurantComponent implements OnInit {

  name = 'restaurant name';
  type = 'veg';
  image: any = '';
  image2: any = '';

  constructor(private appService: AppServiceService, private sanitizer: DomSanitizer) { }

  ngOnInit() {
    this.appService.getRestaurantImage().subscribe(
      (response) => {
        this.image = this.sanitizer.bypassSecurityTrustUrl(response);
        this.image2 = response;
        console.log("got data");
      }, (error) => {
        this.image = "";
        console.log("got error", error);
      }
    );
    // this.image = this.sanitizer.bypassSecurityTrustUrl(this.appService.getRestaurantImage());
  }

}
