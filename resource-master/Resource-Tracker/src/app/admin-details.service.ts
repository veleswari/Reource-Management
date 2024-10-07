import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AdminDetails } from './admin-details.model'; // Define AdminDetails interface/model

@Injectable({
  providedIn: 'root'
})
export class AdminDetailsService {

  private baseUrl = 'http://localhost:8080/api/auth/admin'; // Replace with your actual backend URL

  constructor(private http: HttpClient) { }

  getAllAdminDetails(): Observable<AdminDetails[]> {
    return this.http.get<AdminDetails[]>(`${this.baseUrl}/all`);
  }

  saveAdminDetails(adminDetails: AdminDetails): Observable<AdminDetails> {
    return this.http.post<AdminDetails>(`${this.baseUrl}/save`, adminDetails);
  }

  updateAdminDetails(id: number, adminDetails: AdminDetails): Observable<string> {
    return this.http.put<string>(`${this.baseUrl}/edit/${id}`, adminDetails, { responseType: 'text' as 'json' });
  }

  deleteAdminDetails(id: number): Observable<string> {
    return this.http.delete<string>(`${this.baseUrl}/delete/${id}`, { responseType: 'text' as 'json' });
  }
}
