import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlexDialogComponent } from './flex-dialog.component';

describe('FlexDialogComponent', () => {
  let component: FlexDialogComponent;
  let fixture: ComponentFixture<FlexDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FlexDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FlexDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
