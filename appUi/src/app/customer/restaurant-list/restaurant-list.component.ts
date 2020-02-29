import { Component, Input, OnInit, SimpleChanges, Output, EventEmitter, HostListener } from '@angular/core';
import { RestaurantModel } from '../../FoodOrderApp';
import { Observable, Observer, of } from 'rxjs';
import { AppServiceService } from '../../app-service.service';
import { SessionService } from '../../session.service';
import { LoadBarService } from 'src/app/load-bar.service';
import { ImageResponseCacheService } from 'src/app/image-response-cache.service';

@Component({
  selector: 'app-restaurant-list',
  templateUrl: './restaurant-list.component.html',
  styleUrls: ['./restaurant-list.component.css']
})
export class RestaurantListComponent implements OnInit {

  resImage = '/assets/images/res_small.svg';
  imageVeg = '/assets/images/veg.svg';
  imageNonVeg = '/assets/images/non_veg.svg';
  public resImages: any = {};

  @Input()
  restaurants: RestaurantModel[];
  @Output()
  resSelect = new EventEmitter<RestaurantModel>();
  @Output()
  loadNext = new EventEmitter<boolean>();

  constructor(
    private appService: AppServiceService,
    private session: SessionService,
    public loadBar: LoadBarService,
    private imageCache: ImageResponseCacheService
  ) { }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges): void {
    for (let index in this.restaurants) {
      let res = this.restaurants[index];
      if (res.imageId == null || res.imageId == undefined) {
        this.resImages[res.id] = of('url(' + this.resImage + ')');
      } else if (this.resImages[res.id] != null && this.resImages[res.id] != undefined) {
        continue;
      }
      this.resImages[res.id] = new Observable<any>(
        (observer: Observer<any>) => {
          let cachedImage = this.imageCache.getImage(res.imageId);
          if (cachedImage != null && cachedImage != undefined) {
            observer.next(cachedImage);
          } else {
            this.appService.getRestaurantImage(res.imageId).subscribe(
              (imageData) => {
                this.imageCache.setImage(res.imageId, imageData);
                observer.next(imageData);
              }, (error) => {
                observer.next(this.resImage);
              }
            );
          }
        }
      );
    }
  }

  @HostListener('window:scroll', ['$event'])
  public windowScroll(event: any): void {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
      this.loadNext.emit(true);
    }
  }

  public selectRes(res: RestaurantModel) {
    this.resSelect.emit(res);
  }

}
