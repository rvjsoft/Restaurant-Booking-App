import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustLandingComponent } from './cust-landing.component';

describe('CustLandingComponent', () => {
  let component: CustLandingComponent;
  let fixture: ComponentFixture<CustLandingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustLandingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustLandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
