import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageResponseCacheService {

  private imageCache: {[key: string]: string} = {};

  constructor() { }

  setImage(imageId: string, response: string): void {
    this.imageCache[imageId] = response;
  }

  getImage(imageId: string): string {
    return this.imageCache[imageId];
  }
}
