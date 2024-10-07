import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { FlexFields } from './flex-fields.model';

@Injectable({
  providedIn: 'root'
})
export class FlexFieldsService {

  private baseUrl = 'http://localhost:8080/flexFields'; // Replace with your backend base URL

  constructor(private http: HttpClient) { }
  getAll(): Observable<FlexFields[]> {
    return this.http.get<FlexFields[]>(`${this.baseUrl}/all`);
  }

  getFlexFields(customerName: string, skillSet: string): Observable<string[]> {
    const url = `${this.baseUrl}/values?customerName=${customerName}&skillSet=${skillSet}`;
    return this.http.get<string[]>(url);
  }
  saveFlexField(flexField: FlexFields): Observable<FlexFields> {
    return this.http.post<FlexFields>(`${this.baseUrl}/save`, flexField);
  }
  updateFlexFields(flexFieldsDto: FlexFields): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/update`, flexFieldsDto);
  }
  deleteFlexField(customId: string): Observable<string> {
    return this.http.delete(`${this.baseUrl}/delete`, { 
      params: { customId },
      responseType: 'text'
    });
  }
  getSkillSetForCustomer(customerName: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/skillset`, { params: { customerName } });
  }
  getFlexFieldsBySkillSet(skillSet: string): Observable<FlexFields[]> {
    let params = new HttpParams().set('skillSet', skillSet);
    return this.http.get<FlexFields[]>(`${this.baseUrl}/flex-fields`, { params });
  }
  filter(filter: any): Observable<FlexFields[]> {
    let params = new HttpParams();

    // Dynamically add filter fields to params
    Object.keys(filter).forEach(key => {
      if (filter[key]) {
        params = params.append(key, filter[key]);
      }
    });

    return this.http.get<FlexFields[]>(`${this.baseUrl}/flexfield`, { params });
  }
}
