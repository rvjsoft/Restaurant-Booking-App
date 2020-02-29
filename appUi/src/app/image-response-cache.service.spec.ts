import { TestBed } from '@angular/core/testing';

import { ImageResponseCacheService } from './image-response-cache.service';

describe('ImageResponseCacheService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ImageResponseCacheService = TestBed.get(ImageResponseCacheService);
    expect(service).toBeTruthy();
  });
});
