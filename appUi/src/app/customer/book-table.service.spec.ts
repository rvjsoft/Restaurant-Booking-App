import { TestBed } from '@angular/core/testing';

import { BookTableService } from './book-table.service';

describe('BookTableService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BookTableService = TestBed.get(BookTableService);
    expect(service).toBeTruthy();
  });
});
