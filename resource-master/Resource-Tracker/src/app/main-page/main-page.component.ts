import { Component, OnInit, OnDestroy, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { ResourceDetails } from '../data.model';
import { ProjectService } from '../services/project.service';
import { Router, NavigationEnd } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { takeUntil, filter, map } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { ResourceDialogComponent } from '../resource-dialog/resource-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpResponse } from '@angular/common/http';
import { FlexDialogComponent } from '../flex-dialog/flex-dialog.component';
import Highcharts from 'highcharts';

import { DialogService } from 'primeng/dynamicdialog';
import { SelectItem } from 'primeng/api';
import { MatDialog } from '@angular/material/dialog';
import { animate, state, style, transition, trigger } from '@angular/animations';



@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css'],
 
  providers: [DialogService]
})
export class MainPageComponent implements OnInit, OnDestroy, AfterViewInit {
  customers: string[] = [];
  selectedCustomer: string = '';
  streams: string[] = [];
  selectedStream: string = '';
  currentView: string = 'mainPage'; 
  
  
  // Columns configuration for PrimeNG Table
  cols = [
    { field: 'select', header: 'Select', sortable: false, filter: false },
    { field: 'spid', header: 'SPID', sortable: true, filter: true },
    { field: 'requestedDate', header: 'Requested Date', sortable: true, filter: true },
    { field: 'customerMgrName', header: 'Customer Manager Name', sortable: true, filter: true },
    { field: 'skillSet', header: 'Skill Set', sortable: true, filter: true },
    { field: 'billRate', header: 'Bill Rate', sortable: true, filter: true },
    { field: 'overallStatus', header: 'Overall Status', sortable: true, filter: true },
    { field: 'resourceName', header: 'Resource Name', sortable: true, filter: true },
    { field: 'profileSharedDate', header: 'Profile Shared Date', sortable: true, filter: true },
    { field: 'customerInterviewDate', header: 'Customer Interview Date', sortable: true, filter: true },
    { field: 'dateOfJoin', header: 'Date of Join', sortable: true, filter: true },
    { field: 'internalExternal', header: 'Internal/External', sortable: true, filter: true },
    { field: 'l1InterviewDate', header: 'L1 Interview Date', sortable: true, filter: true },
    { field: 'rating', header: 'Rating', sortable: true, filter: true },

    { field: 'actions', header: 'Actions', sortable: false, filter: false }
  ];

  dataSource: ResourceDetails[] = [];
  originalDataSource: ResourceDetails[] = [];

  public filters: any = {};
  public filterOptions: { [key: string]: SelectItem[] } = {};
  public pagination = {
    page: 0,
    size: 10,
    totalRecords: 0
  };
  fullName: string | null = null;
  private unsubscribe$: Subject<void> = new Subject<void>();
  isDropdownOpen = false;
  statusCounts: { selected: number; rejected: number; inProgress: number } | null = null;
  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {};
 

  constructor(
    private projectService: ProjectService,
    private router: Router,
    private authService: AuthService,
    public dialog: DialogService, // Use PrimeNG DialogService
    private snackBar: MatSnackBar,
    private cdr: ChangeDetectorRef,
    private matDialog: MatDialog
  ) {}

  ngAfterViewInit(): void {
    // No need for MatSort as PrimeNG handles sorting internally
    // this.updateChart();
  }

  ngOnInit(): void {
    console.log('Component initialized');
    this.loadCustomers();
    this.loadFilterOptions();
    this.loadResourceDetails();

    this.fullName = this.authService.getFullName();

    this.authService.onLogout().pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe(() => {
      this.clearDataOnLogout();
    });

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      takeUntil(this.unsubscribe$)
    ).subscribe(() => {
      if (this.fullName && !this.authService.hasShownWelcomeMessage()) {
        this.showWelcomeSnackBar(`Welcome, ${this.fullName}!`);
        this.authService.setWelcomeMessageShown(true); // Set the flag to true after showing the message
      }
      console.log('Navigation ended, pie chart rendered');
    });

    // Show welcome snack bar when user logs in
    if (this.fullName && !this.authService.hasShownWelcomeMessage()) {
      this.showWelcomeSnackBar(`Welcome, ${this.fullName}!`);
      this.authService.setWelcomeMessageShown(true); // Set the flag to true after showing the message
    }
     // Show all records by default
    
  }
  loadFilterOptions(): void {
    const filterFields = this.cols.filter(col => col.filter).map(col => col.field);
    
    filterFields.forEach(field => {
      this.projectService.getResourceDetails({ [field]: '' }).pipe(
        map((response: any[]) => {
          if (Array.isArray(response)) {
            const uniqueValues = Array.from(new Set(response.map(item => String(item[field as keyof ResourceDetails] || '').trim())));
            return uniqueValues.map(value => ({
              label: value,
              value: value
            }));
          } else {
            return [];
          }
        })
      ).subscribe({
        next: (options: SelectItem[]) => {
          this.filterOptions[field] = options;
        },
        error: (err) => {
          console.error(`Failed to load filter options for field ${field}:`, err);
        }
      });
    });
  }
  applyFilter(field: string, value: any): void {
    if (value) {
      this.filters[field] = value;
    } else {
      delete this.filters[field];
    }
  
    console.log('Filters:', this.filters);
  
    this.dataSource = this.originalDataSource.filter(resource => {
      return Object.keys(this.filters).every(key => {
        const filterValue = this.filters[key];
        const resourceValue = String(resource[key as keyof ResourceDetails] || '').toLowerCase();
        return filterValue === '' || resourceValue.includes(filterValue.toLowerCase());
      });
    });
  
    console.log('Filtered Data Source:', this.dataSource);
  
    this.cdr.detectChanges();
  }
  
  getFilterOptions(field: string): SelectItem[] {
    return this.filterOptions[field] || [];
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  loadCustomers(): void {
    this.projectService.getAllCustomers().subscribe(
      (customers: string[]) => {
        this.customers = customers;
      },
      (error) => {
        console.error('Error loading customers:', error);
      }
    );
  }

  onCustomerSelected(): void {
    if (this.selectedCustomer) {
      this.projectService.getStreamForCustomer(this.selectedCustomer).subscribe(
        (streams: string[]) => {
          this.streams = streams;
          this.selectedStream = '';
        },
        (error) => {
          console.error('Error loading streams for customer:', error);
        }
      );
    } else {
      this.streams = [];
      this.selectedStream = '';
    }
  }

  loadResourceDetails(): void {
    this.projectService.getAllResourceDetails().subscribe(
      (data: ResourceDetails[]) => {
        this.originalDataSource = [...data];
        this.loadFilterOptions();
        this.dataSource = data;
        const statusCounts = this.calculateStatusCounts();
        this.renderPieChart(statusCounts);
        this.updateDataDisplay(statusCounts);
        this.cdr.detectChanges();
       

      },
      (error) => {
        console.error('Error loading resource details:', error);
      }
    );
  }

  onStreamSelected(): void {
    if (this.selectedCustomer && this.selectedStream) {
      this.projectService.getAllDetailsByCustomer(this.selectedCustomer, this.selectedStream).subscribe(
        (data: ResourceDetails[]) => {
          this.dataSource = data;
          this.cdr.detectChanges();
        },
        (error) => {
          console.error('Error loading resource details by customer and stream:', error);
        }
      );
    }
  }

  openDialog(resource?: ResourceDetails): void {
    const dialogRef = this.matDialog.open(ResourceDialogComponent, {
      width: '600px',
      data: resource ? { ...resource } : {} as ResourceDetails
    });

    dialogRef.afterClosed().subscribe((result: { action: any; resource: ResourceDetails; }) => {
      console.log('Dialog result:', result);
      if (result) {
        switch (result.action) {
          case 'add':
            this.saveResource(result.resource);
            break;
          case 'edit':
            this.editResource(result.resource);
            break;
          default:
            console.error('Unknown action:', result.action);
        }
      }
    });
  }

  saveResource(resourceDetailsDto: ResourceDetails): void {
    this.projectService.saveResources(resourceDetailsDto).subscribe(
      (savedResource: ResourceDetails) => {
        console.log('Resource saved:', savedResource);
        this.dataSource = [...this.dataSource, savedResource];
        this.cdr.detectChanges();
        this.showSnackBar('Resource added successfully');
      },
      (error) => {
        console.error('Error saving resource:', error);
        this.showSnackBar('Failed to add resource');
      }
    );
  }

  editResource(resourceDetailsDto: ResourceDetails): void {
    this.projectService.editResources(resourceDetailsDto).subscribe(
      (updatedResource: ResourceDetails) => {
        console.log('Resource updated:', updatedResource);
        const index = this.dataSource.findIndex(r => r.customId === updatedResource.customId);
        if (index !== -1) {
          const updatedData = [...this.dataSource];
          updatedData[index] = updatedResource;
          this.dataSource = updatedData;
          this.cdr.detectChanges();
          this.showSnackBar('Resource updated successfully');
        }
      },
      (error) => {
        console.error('Error editing resource:', error);
        this.showSnackBar('Failed to update resource');
      }
    );
  }

  deleteResource(resourceDetails: ResourceDetails): void {
    this.projectService.deleteResources(resourceDetails).subscribe(
      (response: any) => {
        if (typeof response === 'string') {
          this.dataSource = this.dataSource.filter(r => r.customId !== resourceDetails.customId);
          this.cdr.detectChanges();
          this.showSnackBar(response);
        } else {
          console.error('Unexpected response format:', response);
          this.showSnackBar('Failed to delete resource');
        }
      },
      (error) => {
        console.error('Error deleting resource:', error);
        this.showSnackBar('Error deleting resource');
      }
    );
  }

  openFlexDialog(resource: any): void {
    const dialogRef = this.matDialog.open(FlexDialogComponent, {
      width: '300px',
      data: { resource }
    });

    dialogRef.afterClosed().subscribe(result => {
      // Handle the result if needed
      console.log('FlexDialog result:', result);
    });
  }
  showSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000
    });
  }
  updateChart(): void {
    if (this.statusCounts) {
      this.renderPieChart(this.statusCounts);
    }
  }

  calculateStatusCounts(): { selected: number, rejected: number, inProgress: number } {
    const selectedCount = this.dataSource.filter(item => item.overallStatus === 'Selected').length;
    const rejectedCount = this.dataSource.filter(item => item.overallStatus === 'Rejected').length;
    const inProgressCount = this.dataSource.filter(item => item.overallStatus === 'In Progress').length;

    return {
      selected: selectedCount,
      rejected: rejectedCount,
      inProgress: inProgressCount
    };
  }


  renderPieChart(statusCounts: { selected: number, rejected: number, inProgress: number }|null): void {
    if (!statusCounts) {
      // Handle the case where statusCounts is null, perhaps clear the chart
      return;
    }
    this.chartOptions = {
      chart: {
        type: 'pie',
        backgroundColor: 'transparent',
        plotBackgroundColor: undefined,
        plotBorderWidth: undefined,
        plotShadow: false,
      },
      title: {
        text: '',
      },
      tooltip: {
        enabled: false,
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: false,
          },
          showInLegend: true,
          colors: ['#21A366', '#CC0000', '#FA9805'],
          center: ['50%', '50%'],
          size: '80%',
          innerSize: '60%',
          states: {
            hover: {
              enabled: false,
            },
          },
        },
      },
      legend: {
        labelFormatter: function () {
          const point = this as Highcharts.Point;
          return `${point.name}: ${point.y}`;
        },
      },
      series: [{
        type: 'pie',
        name: 'Status Distribution',
        data: [
          { name: 'Selected', y: statusCounts.selected },
          { name: 'Rejected', y: statusCounts.rejected },
          { name: 'In Progress', y: statusCounts.inProgress },
        ],
      }],
      credits: {
        enabled: false,
      },
    };

    Highcharts.chart('pie-chart-container', this.chartOptions);
  }
  updateDataDisplay(statusCounts: { selected: number, rejected: number, inProgress: number }): void {
    const selectedCountElement = document.querySelector('.selected-count');
    const rejectedCountElement = document.querySelector('.rejected-count');
    const inProgressCountElement = document.querySelector('.in-progress-count');

    if (selectedCountElement) {
      selectedCountElement.textContent = statusCounts.selected.toString();
    }
    if (rejectedCountElement) {
      rejectedCountElement.textContent = statusCounts.rejected.toString();
    }
    if (inProgressCountElement) {
      inProgressCountElement.textContent = statusCounts.inProgress.toString();
    }
  }

  clearDataOnLogout(): void {
    this.dataSource = [];
    this.customers = [];
    this.streams = [];
    this.selectedCustomer = '';
    this.selectedStream = '';
  
  }

  showWelcomeSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      verticalPosition: 'top', // Position snack bar at the top
      horizontalPosition: 'center',
    });
  }


  downloadFile(): void {
    this.projectService.downloadFile().subscribe(
      (response: HttpResponse<Blob>) => {
        const contentDispositionHeader = response.headers.get('Content-Disposition');
        const filename = contentDispositionHeader ? contentDispositionHeader.split(';')[1].split('filename=')[1].trim() : 'file';
  
        const blob = new Blob([response.body!], { type: response.body!.type });
        const link = document.createElement('a');
        link.style.display = 'none';
        document.body.appendChild(link);
  
        const url = window.URL.createObjectURL(blob);
        link.href = url;
        link.download = filename;
  
        link.click();
  
        window.URL.revokeObjectURL(url);
        document.body.removeChild(link);
  
        this.showSnackBar('File downloaded successfully');
      },
      (error) => {
        console.error('Error downloading file:', error);
        this.showSnackBar('Error downloading file');
      }
    );
  }
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/overview']);
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'Selected':
        return '#21A366'; // Green for 'Selected'
      case 'Rejected':
        return '#CC0000'; // Red for 'Rejected'
      case 'In Progress':
        return '#FA9805'; // Yellow for 'In Progress'
      default:
        return '#FFFFFF'; // Default color (white) if no match
    }
  }
  applyGlobalFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    
    if (filterValue === '') {
      // Reset to original data when search is cleared
      this.dataSource = [...this.originalDataSource];
    } else {
      this.dataSource = this.originalDataSource.filter((resource: ResourceDetails) => {
        return Object.keys(resource).some((key: string) => {
          const value = String(resource[key as keyof ResourceDetails]).toLowerCase();
          return value.includes(filterValue);
        });
      });
    }
    
    // Optionally, you can trigger change detection manually
    this.cdr.detectChanges();
  }
  
}
