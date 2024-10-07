import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FlexFieldsService } from '../flex-fields.service';

@Component({
  selector: 'app-flex-dialog',
  templateUrl: './flex-dialog.component.html',
  styleUrls: ['./flex-dialog.component.css']
})
export class FlexDialogComponent implements OnInit {
  resource: any;
  flexFields: string[] = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private flexFieldsService: FlexFieldsService
  ) {
    this.resource = data.resource;
  }

  ngOnInit(): void {
    if (this.resource?.customerName && this.resource?.skillSet) {
      this.fetchFlexFields(this.resource.customerName, this.resource.skillSet);
    }
  }

  fetchFlexFields(customerName: string, skillSet: string): void {
    this.flexFieldsService.getFlexFields(customerName, skillSet).subscribe(
      flexFields => {
        // Transform flex fields to include prefix "flexfield_"
        this.flexFields = flexFields.map(field => `flexfield_${field}`);
        
        // Update resource object with fetched flex fields
        this.resource.flexFields = flexFields; // Assuming you want to keep track of all flex fields
      },
      error => {
        console.error('Error fetching flex fields:', error);
        // Handle error as needed
      }
    );
  }
}
