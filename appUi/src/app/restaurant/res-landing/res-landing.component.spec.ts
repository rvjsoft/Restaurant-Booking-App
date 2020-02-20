import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResLandingComponent } from './res-landing.component';

describe('ResLandingComponent', () => {
  let component: ResLandingComponent;
  let fixture: ComponentFixture<ResLandingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResLandingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResLandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
