import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TablesHistoryComponent } from './tables-history.component';

describe('TablesHistoryComponent', () => {
  let component: TablesHistoryComponent;
  let fixture: ComponentFixture<TablesHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TablesHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TablesHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
