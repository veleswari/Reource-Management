import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSelectModule } from '@angular/material/select';
import { MatNativeDateModule } from '@angular/material/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { OverviewComponent } from './overview/overview.component';
import { LoginComponent } from './login/login.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { TableModule } from 'primeng/table';import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { MainPageComponent } from './main-page/main-page.component';
import { AuthInterceptor } from './auth.interceptor';
import { AuthService } from './services/auth.service';
import { AuthGuard } from './auth.guard';
import { ResourceDialogComponent } from './resource-dialog/resource-dialog.component';
import { CustomerStatusComponent } from './customer-status/customer-status.component';
import { AdminDetailsComponent } from './admin-details/admin-details.component';
import { AdminDialogComponent } from './admin-dialog/admin-dialog.component';
import { FlexDialogComponent } from './flex-dialog/flex-dialog.component';
import { FlexFieldsComponent } from './flex-fields/flex-fields.component';
import { FlexFieldsDialogComponent } from './flex-fields-dialog/flex-fields-dialog.component';
import { AgGridModule } from 'ag-grid-angular';
import { ChartModule } from 'angular-highcharts';
import { MatCheckboxModule } from '@angular/material/checkbox';
import * as Highcharts from 'highcharts';
import Highcharts3D from 'highcharts/highcharts-3d';
import { PaginatorModule } from 'primeng/paginator';
import { DropdownModule } from 'primeng/dropdown';
import { HighchartsChartModule } from 'highcharts-angular';
import { DialogModule } from 'primeng/dialog';
import { CalendarModule } from 'primeng/calendar';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast'; 
import { ListboxModule } from 'primeng/listbox';
import { PanelModule } from 'primeng/panel';
import { MessageService } from 'primeng/api';
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { MenuModule } from 'primeng/menu';
import { SharedLayoutComponent } from './shared-layout/shared-layout.component';
// Apply the Highcharts 3D module
Highcharts3D(Highcharts);
@NgModule({
  declarations: [
    AppComponent,
    OverviewComponent,
 
  

    LoginComponent,
    MainPageComponent,
    ResourceDialogComponent,
    CustomerStatusComponent,
    AdminDetailsComponent,
    AdminDialogComponent,
    FlexDialogComponent,
    FlexFieldsComponent,
    FlexFieldsDialogComponent,
    SharedLayoutComponent
   
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatDialogModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatSelectModule,
    MatNativeDateModule,
    HttpClientModule,
    FormsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatSnackBarModule,
    PaginatorModule,
    DropdownModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    ChartModule,
    MatCheckboxModule ,
    HighchartsChartModule,
    DialogModule,
    CalendarModule,
    MessageModule,
    ToastModule,
    ListboxModule,
    PanelModule,
    DynamicDialogModule,
    OverlayPanelModule,
    MenuModule,
  ],
  providers: [
    AuthService,
    AuthGuard,
   MessageService,
   DialogService,
    
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
