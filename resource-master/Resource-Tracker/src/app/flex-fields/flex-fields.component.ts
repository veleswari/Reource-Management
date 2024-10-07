import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { FlexFields } from '../flex-fields.model';
import { FlexFieldsService } from '../flex-fields.service';
import { FlexFieldsDialogComponent } from '../flex-fields-dialog/flex-fields-dialog.component';

@Component({
  selector: 'app-flex-fields',
  templateUrl: './flex-fields.component.html',
  styleUrls: ['./flex-fields.component.css']
})
export class FlexFieldsComponent implements OnInit {

  flexFields: FlexFields[] = [];
  totalRecords: number = 0;
  loading: boolean = false;

  // Dropdown options for filters
  customIdOptions: any[] = [];
  customerNameOptions: any[] = [];
  skillSetOptions: any[] = [];
  flexValueOptions: any[] = [];

  // Columns configuration for PrimeNG Table
  cols = [
    { field: 'customId', header: 'Custom ID', sortable: true, filter: true },
    { field: 'customerName', header: 'Customer Name', sortable: true, filter: true },
    { field: 'skillSet', header: 'Skill Set', sortable: true, filter: true },
    { field: 'flexValue', header: 'Flex Value', sortable: true, filter: true },
    { field: 'actions', header: 'Actions', sortable: false, filter: false }
  ];

  // Filter values
  selectedFilters: any = {};

  constructor(private flexFieldsService: FlexFieldsService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadFilterOptions();
    this.loadFlexFields();
  }

  // Load options for dropdown filters
  loadFilterOptions(): void {
    this.flexFieldsService.getAll().subscribe(fields => {
      this.customIdOptions = this.generateFilterOptions(fields, 'customId');
      this.customerNameOptions = this.generateFilterOptions(fields, 'customerName');
      this.skillSetOptions = this.generateFilterOptions(fields, 'skillSet');
      this.flexValueOptions = this.generateFilterOptions(fields, 'flexValue');
    });
  }

  // Helper method to generate unique filter options
  generateFilterOptions(data: any[], field: string): any[] {
    const options = data.map(item => ({ label: item[field], value: item[field] }));
    return Array.from(new Set(options.map(option => JSON.stringify(option))))
      .map(option => JSON.parse(option)); // Remove duplicates
  }

  // Load Flex Fields with filters
  loadFlexFields(): void {
    this.loading = true;

    const filterParams = {
      ...this.selectedFilters, // Add filter values to the request
      // Pagination and sorting are removed
    };

    this.flexFieldsService.filter(filterParams).subscribe(data => {
      this.flexFields = data;
      this.totalRecords = this.flexFields.length;
      this.loading = false;
    });
  }

  // Event triggered when a filter is applied
  applyFilter(event: any, field: string): void {
    this.selectedFilters[field] = event.value;
    this.loadFlexFields();  // Reload the data with new filters
  }

  // Dialog functions for adding/editing Flex Fields
  openAddDialog(): void {
    const dialogRef = this.dialog.open(FlexFieldsDialogComponent, {
      width: '500px',
      data: { action: 'add', flexField: {} as FlexFields }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.action === 'save') {
        this.saveFlexField(result.flexField);
      }
    });
  }

  openEditDialog(flexField: FlexFields): void {
    const dialogRef = this.dialog.open(FlexFieldsDialogComponent, {
      width: '500px',
      data: { action: 'edit', flexField }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.action === 'save') {
        this.updateFlexField(result.flexField);
      }
    });
  }

  saveFlexField(flexField: FlexFields): void {
    this.flexFieldsService.saveFlexField(flexField)
      .subscribe(() => this.loadFlexFields());
  }

  updateFlexField(flexField: FlexFields): void {
    this.flexFieldsService.updateFlexFields(flexField)
      .subscribe(() => this.loadFlexFields());
  }

  deleteFlexField(customId: string): void {
    this.flexFieldsService.deleteFlexField(customId)
      .subscribe(() => this.loadFlexFields());
  }
}
