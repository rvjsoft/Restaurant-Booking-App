import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageResponseCacheService {

  private imageCache: {[key: string]: Observable<any>} = {};

  constructor() { }

  setImage(imageId: string, response: Observable<any>): void {
    this.imageCache[imageId] = response;
  }

  getImage(imageId: string): Observable<any> {
    return this.imageCache[imageId];
  }
}
