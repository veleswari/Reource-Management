import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDialogComponent } from './admin-dialog.component';

describe('AdminDialogComponent', () => {
  let component: AdminDialogComponent;
  let fixture: ComponentFixture<AdminDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
