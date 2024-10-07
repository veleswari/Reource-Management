import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Observable } from 'rxjs';
import { ResourceDetails } from '../data.model';
import { ProjectService } from '../services/project.service';
import { map } from 'rxjs/operators';
import { SelectItem } from 'primeng/api';
import { LoginComponent } from '../login/login.component';
import { MatDialog } from '@angular/material/dialog';
import Highcharts from 'highcharts';

interface Column {
  field: string;
  header: string;
  sortable: boolean;
  filter: boolean;
}

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
  public rowData: ResourceDetails[] = [];
  public columns: Column[] = [
    { field: 'customId', header: 'ID', sortable: true, filter: true },
    { field: 'spid', header: 'SPID', sortable: true, filter: true },
    { field: 'requestedDate', header: 'Requested Date', sortable: true, filter: true },
    { field: 'customerMgrName', header: 'Customer Manager', sortable: true, filter: true },
    { field: 'skillSet', header: 'Skill Set', sortable: true, filter: true },
    { field: 'billRate', header: 'Bill Rate', sortable: true, filter: true },
    { field: 'overallStatus', header: 'Overall Status', sortable: true, filter: true },
    { field: 'internalExternal', header: 'Internal/External', sortable: true, filter: true },
    { field: 'resourceName', header: 'Resource Name', sortable: true, filter: true },
    { field: 'noOfYears', header: 'No. of Years', sortable: true, filter: true },
    { field: 'profileSharedDate', header: 'Profile Shared Date', sortable: true, filter: true },
    { field: 'customerInterviewDate', header: 'Customer Interview Date', sortable: true, filter: true },
    { field: 'l1InterviewDate', header: 'L1 Interview Date', sortable: true, filter: true },
    { field: 'dateOfJoin', header: 'Date of Join', sortable: true, filter: true },
    { field: 'flexField1Rating', header: 'Flex Field 1 Rating', sortable: true, filter: true },
    { field: 'flexField2Rating', header: 'Flex Field 2 Rating', sortable: true, filter: true },
    { field: 'flexField3Rating', header: 'Flex Field 3 Rating', sortable: true, filter: true },
    { field: 'flexField4Rating', header: 'Flex Field 4 Rating', sortable: true, filter: true },
    { field: 'customerName', header: 'Customer Name', sortable: true, filter: true },
    { field: 'stream', header: 'Stream', sortable: true, filter: true },
    { field: 'opptyName', header: 'Opportunity Name', sortable: true, filter: true },
    { field: 'managerName', header: 'Manager Name', sortable: true, filter: true },
    { field: 'flexValue', header: 'Flex Value', sortable: true, filter: true }
  ];
  
  public pagination = {
    page: 0,
    size: 10,
    totalRecords: 0
  };
  public filters: any = {};
  public filterOptions: { [key: string]: SelectItem[] } = {};
  public sortField: string = '';
  public sortOrder: number = 1;
  
  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {};

  constructor(
    private overviewService: ProjectService,
    private cd: ChangeDetectorRef,
    private dialog: MatDialog 
  ) {}

  ngOnInit(): void {
    this.loadFilterOptions();
    this.loadPage(this.pagination.page, this.pagination.size);
  }

  loadPage(page: number, size: number): void {
    const params: { [key: string]: string } = {
      page: page.toString(),
      size: size.toString(),
      sortField: this.sortField || '',
      sortOrder: this.sortOrder.toString(),
      ...this.filters
    };

    this.overviewService.getResourceDetails(params)
      .pipe(
        map(response => {
          this.rowData = Array.isArray(response) ? response : [];
          this.pagination.totalRecords = this.rowData.length;
          this.cd.detectChanges(); // Trigger change detection
          const statusCounts = this.calculateStatusCounts();
          this.renderPieChart(statusCounts);
          this.updateDataDisplay(statusCounts);
        })
      )
      .subscribe({
        error: (err) => {
          console.error('Error loading data:', err);
        }
      });
  }

  onPageChange(event: any): void {
    this.pagination.page = event.page;
    this.pagination.size = event.rows;
    this.loadPage(this.pagination.page, this.pagination.size);
  }

  onSortChange(event: any): void {
    this.sortField = event.sortField || '';
    this.sortOrder = event.sortOrder || 1;
    this.loadPage(this.pagination.page, this.pagination.size);
  }

  onFilterChange(filterField: string, filterValue: string): void {
    if (filterValue) {
      this.filters[filterField] = filterValue.trim();
    } else {
      delete this.filters[filterField];
    }
    this.loadPage(this.pagination.page, this.pagination.size);
  }

  loadFilterOptions(): void {
    const filterFields = this.columns.filter(col => col.filter).map(col => col.field);
    
    filterFields.forEach(field => {
      this.overviewService.getResourceDetails({ [field]: '' }).pipe(
        map(response => {
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

  getFilterOptions(field: string): SelectItem[] {
    return this.filterOptions[field] || [];
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

  login(): void {
    const dialogRef = this.dialog.open(LoginComponent, {
      width: '300px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.authenticated) {
        this.loadPage(this.pagination.page, this.pagination.size);
      } else {
        console.log('Login was canceled or unsuccessful');
      }
    });
  }

  calculateStatusCounts(): { selected: number, rejected: number, inProgress: number } {
    const selectedCount = this.rowData.filter(item => item.overallStatus === 'Selected').length;
    const rejectedCount = this.rowData.filter(item => item.overallStatus === 'Rejected').length;
    const inProgressCount = this.rowData.filter(item => item.overallStatus === 'In Progress').length;

    return {
      selected: selectedCount,
      rejected: rejectedCount,
      inProgress: inProgressCount
    };
  }

  renderPieChart(statusCounts: { selected: number, rejected: number, inProgress: number }): void {
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
}
