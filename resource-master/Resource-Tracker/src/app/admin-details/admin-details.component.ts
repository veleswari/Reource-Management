import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AdminDialogComponent } from '../admin-dialog/admin-dialog.component';
import { AdminDetails } from '../admin-details.model';
import { AdminDetailsService } from '../admin-details.service';
import { MessageService } from 'primeng/api'; // Import MessageService from PrimeNG
import { AuthService } from '../services/auth.service';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-admin-details',
  templateUrl: './admin-details.component.html',
  styleUrls: ['./admin-details.component.css'],
  animations: [
    trigger('slideInOut', [
      state('in', style({
        transform: 'translateX(0%)',
      })),
      state('out', style({
        transform: 'translateX(-100%)',
      })),
      transition('in <=> out', [
        animate('300ms ease-in-out')
      ]),
    ])
  ],
  providers: [MessageService] // Provide MessageService
})
export class AdminDetailsComponent implements OnInit {

  adminDetailsList: AdminDetails[] = [];

  constructor(
    private dialog: MatDialog, // Use MatDialog for Angular Material dialogs
    private adminService: AdminDetailsService,
    private messageService: MessageService, // Use MessageService instead of ToastService
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.fetchAdminDetails();
  }

  fetchAdminDetails(): void {
    this.adminService.getAllAdminDetails()
      .subscribe(
        (data: AdminDetails[]) => {
          this.adminDetailsList = data;
        },
        (error: any) => {
          console.error('Error fetching admin details:', error);
        }
      );
  }

  openAddAdminDialog(): void {
    const dialogRef = this.dialog.open(AdminDialogComponent, {
      width: '400px',
      data: { managerName: this.authService.getFullName() || '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.action === 'add') {
        this.adminService.saveAdminDetails(result.admin).subscribe(
          (data: any) => {
            this.openMessage('Admin details added successfully');
            this.fetchAdminDetails(); // Refresh list after add
          },
          (error: any) => {
            this.openMessage('Failed to add admin details', 'error');
            console.error('Error adding admin details:', error);
          }
        );
      }
    });
  }

  openEditAdminDialog(admin: AdminDetails): void {
    const dialogRef = this.dialog.open(AdminDialogComponent, {
      width: '400px',
      data: admin
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.action === 'edit' && admin.id !== undefined) {
        this.adminService.updateAdminDetails(admin.id, result.admin).subscribe(
          (data: any) => {
            this.openMessage('Admin details updated successfully');
            this.fetchAdminDetails(); // Refresh list after edit
          },
          (error: any) => {
            this.openMessage('Failed to update admin details', 'error');
            console.error('Error updating admin details:', error);
          }
        );
      }
    });
  }

  deleteAdmin(id: number): void {
    if (confirm('Are you sure you want to delete this admin detail?')) {
      this.adminService.deleteAdminDetails(id).subscribe(
        (response: any) => {
          this.openMessage('Admin details deleted successfully');
          this.fetchAdminDetails(); // Refresh list after delete
        },
        (error: any) => {
          this.openMessage('Failed to delete admin details', 'error');
          console.error('Error deleting admin details:', error);
        }
      );
    }
  }

  openMessage(message: string, severity: string = 'success'): void {
    this.messageService.add({ severity: severity, summary: message });
  }
}
