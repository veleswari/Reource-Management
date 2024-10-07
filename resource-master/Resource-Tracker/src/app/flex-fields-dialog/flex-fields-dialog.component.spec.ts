import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlexFieldsDialogComponent } from './flex-fields-dialog.component';

describe('FlexFieldsDialogComponent', () => {
  let component: FlexFieldsDialogComponent;
  let fixture: ComponentFixture<FlexFieldsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FlexFieldsDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FlexFieldsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
