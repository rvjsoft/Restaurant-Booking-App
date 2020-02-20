import { TestBed } from '@angular/core/testing';

import { LoadBarService } from './load-bar.service';

describe('LoadBarService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LoadBarService = TestBed.get(LoadBarService);
    expect(service).toBeTruthy();
  });
});
