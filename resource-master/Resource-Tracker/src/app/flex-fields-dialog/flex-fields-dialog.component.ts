import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FlexFields } from '../flex-fields.model';

@Component({
  selector: 'app-flex-fields-dialog',
  templateUrl: './flex-fields-dialog.component.html',
  styleUrls: ['./flex-fields-dialog.component.css']
})
export class FlexFieldsDialogComponent {
  flexField: FlexFields;

  constructor(
    public dialogRef: MatDialogRef<FlexFieldsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { action: string, flexField: FlexFields }
  ) {
    // Create a copy of the data if editing, otherwise initialize a new object
    this.flexField = data.action === 'edit' ? { ...data.flexField } : { customId: '', customerName: '', skillSet: '', flexValue: '',managerName:'' };
  }

  onSave(): void {
    this.dialogRef.close({ action: 'save', flexField: this.flexField });
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
