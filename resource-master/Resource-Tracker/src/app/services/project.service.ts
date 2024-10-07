import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResourceDetails } from '../data.model';
import { Page } from '../page.model';


@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private baseUrl = 'http://localhost:8080/api/auth'; // Update the URL as needed

  constructor(private http: HttpClient) {}
  getAllDetailsByPagination(page: number, size: number): Observable<Page<ResourceDetails>> {
    let params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<Page<ResourceDetails>>(`${this.baseUrl}/resource/overview`, { params });
  }

  getAllCustomers(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/filter/manager`);
  }

  getStreamForCustomer(customerName: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/filter/stream`, {
      params: { customerName }
    });
  }

  getAllResourceDetails(): Observable<ResourceDetails[]> {
    return this.http.get<ResourceDetails[]>(`${this.baseUrl}/resource/all`);
  }
  getAllDetailsByCustomer(customerName: string, stream: string): Observable<ResourceDetails[]> {
    const url = `${this.baseUrl}/resource/${customerName}/${stream}`;
    return this.http.get<ResourceDetails[]>(url);
  }

  saveResources(resourceDetailsDto: ResourceDetails): Observable<ResourceDetails> {
    const url = `${this.baseUrl}/resource/save`;
    return this.http.post<ResourceDetails>(url, resourceDetailsDto);
  }

  editResources(resourceDetailsDto: ResourceDetails): Observable<ResourceDetails> {
    const url = `${this.baseUrl}/resource/update`;
    return this.http.put<ResourceDetails>(url, resourceDetailsDto);
  }

  deleteResources(resourceDetails: ResourceDetails): Observable<string> {
    return this.http.delete(`${this.baseUrl}/resource/remove`, { 
      body: resourceDetails,
      responseType: 'text' 
    });
  }
  downloadFile(): Observable<HttpResponse<Blob>> {
    const url = `${this.baseUrl}/resource/download`; // Replace with your backend endpoint for file download
    return this.http.get(url, {
      responseType: 'blob',
      observe: 'response'
    });
  }
  getManagerDetails(filterParams: any, sortBy: string = 'managerName', direction: string = 'asc'): Observable<ResourceDetails[]> {
    let params = new HttpParams();

    // Append filter params
    Object.keys(filterParams).forEach(key => {
      if (filterParams[key]) {
        params = params.append(key, filterParams[key]);
      }
    });

    // Append sorting params
    if (direction.toLowerCase() === 'oldest') {
      sortBy = 'requestedDate';  // Assuming 'requestedDate' is the date field to sort by
      direction = 'asc';
    } else if (direction.toLowerCase() === 'newest') {
      sortBy = 'requestedDate';
      direction = 'desc';
    }
    params = params.append('sortBy', sortBy);
    params = params.append('direction', direction);

    return this.http.get<ResourceDetails[]>(`${this.baseUrl}/resource/manager-details`, { params });
  }
  getResourceDetails(filters: any): Observable<ResourceDetails[]> { // Changed from Page<ResourceDetails> to ResourceDetails[]
    let params = new HttpParams();
    for (const key in filters) {
      if (filters[key]) {
        params = params.append(key, filters[key]);
      }
    }
    return this.http.get<ResourceDetails[]>(`${this.baseUrl}/resource/filter`, { params });
  }
  
  
}