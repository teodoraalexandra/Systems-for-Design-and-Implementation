import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalNewComponent } from './rental-new.component';

describe('RentalNewComponent', () => {
  let component: RentalNewComponent;
  let fixture: ComponentFixture<RentalNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentalNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentalNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
