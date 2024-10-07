import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlexFieldsComponent } from './flex-fields.component';

describe('FlexFieldsComponent', () => {
  let component: FlexFieldsComponent;
  let fixture: ComponentFixture<FlexFieldsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FlexFieldsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FlexFieldsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
