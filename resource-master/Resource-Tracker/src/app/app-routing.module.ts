import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { OverviewComponent } from './overview/overview.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';
import { MainPageComponent } from './main-page/main-page.component';
import { CustomerStatusComponent } from './customer-status/customer-status.component';
import { AdminDetailsComponent } from './admin-details/admin-details.component';
// import { FlexDialogComponent } from './flex-dialog/flex-dialog.component';
import { FlexFieldsComponent } from './flex-fields/flex-fields.component';
import { SharedLayoutComponent } from './shared-layout/shared-layout.component';

const routes: Routes = [
  { path: '', redirectTo: '/overview', pathMatch: 'full' },
  { path: 'overview', component: OverviewComponent },
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: SharedLayoutComponent,  // SharedLayoutComponent applies header/footer
    children: [
      { path: 'main-page', component: MainPageComponent, canActivate: [AuthGuard] },
      { path: 'admin', component: AdminDetailsComponent, canActivate: [AuthGuard] },
      { path: 'flex-fields', component: FlexFieldsComponent, canActivate: [AuthGuard] }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
