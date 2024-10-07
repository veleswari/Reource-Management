// customer-track.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomerTrack } from './customer-track.model';
@Injectable({
  providedIn: 'root'
})
export class CustomerTrackService {

  private apiUrl = `http://localhost:8080/customerTrack`;

  constructor(private http: HttpClient) { }

  getAllCustomerTracks(): Observable<CustomerTrack[]> {
    return this.http.get<CustomerTrack[]>(`${this.apiUrl}/all`);
  }

  createCustomerTrack(customerTrackDto: CustomerTrack): Observable<CustomerTrack> {
    return this.http.post<CustomerTrack>(`${this.apiUrl}/save`, customerTrackDto);
  }

  deleteCustomerTrack(customerName: string, track: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${customerName}/${track}`);
  }
}
