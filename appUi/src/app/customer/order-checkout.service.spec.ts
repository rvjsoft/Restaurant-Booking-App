import { TestBed } from '@angular/core/testing';

import { OrderCheckoutService } from './order-checkout.service';

describe('OrderCheckoutService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OrderCheckoutService = TestBed.get(OrderCheckoutService);
    expect(service).toBeTruthy();
  });
});
