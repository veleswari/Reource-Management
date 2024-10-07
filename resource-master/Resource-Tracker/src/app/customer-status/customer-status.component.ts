import { Component, OnInit } from '@angular/core';
import { CustomerStatus } from '../customer-status.model';
import { CustomerTrack } from '../customer-track.model';
import { MessageService } from 'primeng/api'; // Use MessageService for PrimeNG notifications
import { CustomerStatusService } from '../customer-status.service';
import { CustomerTrackService } from '../customer-track.service';

@Component({
  selector: 'app-customer-status',
  templateUrl: './customer-status.component.html',
  styleUrls: ['./customer-status.component.css'],
  providers: [MessageService]
})
export class CustomerStatusComponent implements OnInit {

  customerStatuses: CustomerStatus[] = [];
  customerTracks: CustomerTrack[] = [];

  newCustomerStatus: CustomerStatus = {
    customerName: '',
    status: ''
  };

  newCustomerTrack: CustomerTrack = {
    customerName: '',
    stream: ''
  };

  constructor(
    private customerStatusService: CustomerStatusService,
    private customerTrackService: CustomerTrackService,
    private messageService: MessageService // Use MessageService for PrimeNG notifications
  ) { }

  ngOnInit(): void {
    this.loadCustomerData();
  }

  loadCustomerData(): void {
    this.customerStatusService.getAllCustomerStatuses().subscribe(
      (statuses: CustomerStatus[]) => {
        this.customerStatuses = statuses;
      },
      (error) => {
        console.error('Error loading customer statuses:', error);
      }
    );

    this.customerTrackService.getAllCustomerTracks().subscribe(
      (tracks: CustomerTrack[]) => {
        this.customerTracks = tracks;
      },
      (error) => {
        console.error('Error loading customer tracks:', error);
      }
    );
  }

  createCustomerStatus(): void {
    this.customerStatusService.createCustomerStatus(this.newCustomerStatus).subscribe(
      (createdStatus: CustomerStatus) => {
        console.log('Customer status created:', createdStatus);
        this.customerStatuses.push(createdStatus); // Add newly created status to the list
        this.newCustomerStatus = { customerName: '', status: '' }; // Clear the form fields
        this.showMessage('success', 'Customer status created successfully');
      },
      (error) => {
        console.error('Error creating customer status:', error);
        this.showMessage('error', 'Failed to create customer status');
      }
    );
  }

  createCustomerTrack(): void {
    this.customerTrackService.createCustomerTrack(this.newCustomerTrack).subscribe(
      (createdTrack: CustomerTrack) => {
        console.log('Customer track created:', createdTrack);
        this.customerTracks.push(createdTrack); // Add newly created track to the list
        this.newCustomerTrack = { customerName: '', stream: '' }; // Clear the form fields
        this.showMessage('success', 'Customer track created successfully');
      },
      (error) => {
        console.error('Error creating customer track:', error);
        this.showMessage('error', 'Failed to create customer track');
      }
    );
  }

  deleteCustomerStatus(customerName: string, status: string): void {
    this.customerStatusService.deleteCustomerStatus(customerName, status).subscribe(
      () => {
        console.log(`Customer status deleted: ${customerName} - ${status}`);
        this.customerStatuses = this.customerStatuses.filter(item => item.customerName !== customerName || item.status !== status);
        this.showMessage('success', `Customer status ${customerName} - ${status} deleted successfully`);
      },
      (error) => {
        console.error('Error deleting customer status:', error);
        this.showMessage('error', 'Failed to delete customer status');
      }
    );
  }

  deleteCustomerTrack(customerName: string, track: string): void {
    this.customerTrackService.deleteCustomerTrack(customerName, track).subscribe(
      () => {
        console.log(`Customer track deleted: ${customerName} - ${track}`);
        this.customerTracks = this.customerTracks.filter(item => item.customerName !== customerName || item.stream !== track);
        this.showMessage('success', `Customer track ${customerName} - ${track} deleted successfully`);
      },
      (error) => {
        console.error('Error deleting customer track:', error);
        this.showMessage('error', 'Failed to delete customer track');
      }
    );
  }

  showMessage(severity: string, summary: string): void {
    this.messageService.add({ severity, summary });
  }

}
