import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { RestaurantModel } from '../../FoodOrderApp';
import { Observable, Observer, of } from 'rxjs';
import { AppServiceService } from 'src/app/app-service.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-restaurant-list',
  templateUrl: './restaurant-list.component.html',
  styleUrls: ['./restaurant-list.component.css']
})
export class RestaurantListComponent implements OnInit {

  private resImage = '/assets/images/res_small.svg';
  imageVeg = '/assets/images/veg.svg';
  imageNonVeg = '/assets/images/non_veg.svg';
  private resImages: any = {};

  @Input()
  restaurants: RestaurantModel[];

  constructor(private appService: AppServiceService, private sanitizer: DomSanitizer) { }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges): void {
    for(let index in this.restaurants) {
      let res = this.restaurants[index];
      if(res.imageId == null || res.imageId == undefined) {
        this.resImages[res.id] = of('url(' + this.resImage + ')');
      }
      this.resImages[res.id] = new Observable<any>(
        (observer: Observer<any>) => {
            this.appService.getRestaurantImage(res.imageId).subscribe(
              (imageData) => {
                observer.next(imageData);
              }, (error) => {
                observer.next(this.resImage);
              }
            );
          }
      );
    }
  }

}
