// customer-status.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomerStatus } from './customer-status.model';



@Injectable({
  providedIn: 'root'
})
export class CustomerStatusService {

  private apiUrl = `http://localhost:8080/customerStatus`;

  constructor(private http: HttpClient) { }

  getAllCustomerStatuses(): Observable<CustomerStatus[]> {
    return this.http.get<CustomerStatus[]>(`${this.apiUrl}/all`);
  }

  createCustomerStatus(customerStatusDto: CustomerStatus): Observable<CustomerStatus> {
    return this.http.post<CustomerStatus>(`${this.apiUrl}/save`, customerStatusDto);
  }

  deleteCustomerStatus(customerName: string, status: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${customerName}/${status}`);
  }

  // Uncomment and implement update method if needed
  // updateCustomerStatus(customerStatusDto: CustomerStatusDto): Observable<CustomerStatus> {
  //   return this.http.put<CustomerStatus>(`${this.apiUrl}/update`, customerStatusDto);
  // }
}
