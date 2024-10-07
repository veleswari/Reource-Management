import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminDetails } from '../admin-details.model'; // Adjust path as per your structure

@Component({
  selector: 'app-admin-dialog',
  templateUrl: './admin-dialog.component.html',
  styleUrls: ['./admin-dialog.component.css']
})
export class AdminDialogComponent implements OnInit {
  adminForm: FormGroup;
  isNewAdmin: boolean;

  constructor(
    public dialogRef: MatDialogRef<AdminDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AdminDetails,
    private fb: FormBuilder
  ) {
    this.isNewAdmin = !this.data.id; // Check if id exists to determine if it's a new admin
    this.adminForm = this.createAdminForm(this.data);
  }

  ngOnInit(): void {
  }

  createAdminForm(admin: AdminDetails): FormGroup {
    return this.fb.group({
      id: [admin.id || null], // Add id field with default value of null or admin.id
      customerName: [admin.customerName || '', Validators.required],
      track: [admin.track || '', Validators.required],
      status: [admin.status || '', Validators.required],
      managerName: [admin.managerName || '', Validators.required]
    });
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.adminForm.valid) {
      const formData = this.adminForm.value;
      this.dialogRef.close({ action: this.isNewAdmin ? 'add' : 'edit', admin: formData });
    }
  }
}
