import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyTablesComponent } from './modify-tables.component';

describe('ModifyTablesComponent', () => {
  let component: ModifyTablesComponent;
  let fixture: ComponentFixture<ModifyTablesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModifyTablesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifyTablesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
