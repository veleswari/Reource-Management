import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';
  private tokenKey = 'token';
  private fullNameKey = 'fullName';
  private hasSeenWelcomeMessageKey = 'hasSeenWelcomeMessage';

  private logoutSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {
    window.addEventListener('beforeunload', () => {
      this.clearTokenOnClose();
    });
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/login`, { email, password })
      .pipe(
        tap(response => {
          this.storeToken(response.token);
          this.storeFullName(response.fullName);
          this.setWelcomeMessageShown(false); // Reset welcome message flag on login
        }),
        catchError(error => {
          return throwError(error);
        })
      );
  }

  refreshToken(): Observable<any> {
    const token = this.getToken();
    return this.http.post<any>(`${this.baseUrl}/refresh`, {}, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).pipe(
        tap(response => this.storeToken(response.token)),
        catchError(error => {
            return throwError(error);
        })
    );
  }

  logout(): void {
    this.clearToken();
    this.logoutSubject.next(true);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getFullName(): string | null {
    return localStorage.getItem(this.fullNameKey);
  }

  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }

  onLogout(): Observable<boolean> {
    return this.logoutSubject.asObservable();
  }

  private storeToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  private storeFullName(fullName: string): void {
    localStorage.setItem(this.fullNameKey, fullName);
  }

  private clearTokenOnClose(): void {
    this.clearToken();
  }

  private clearToken(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.fullNameKey);
    localStorage.removeItem(this.hasSeenWelcomeMessageKey); // Remove welcome message flag
    this.logoutSubject.next(true);
  }

  setWelcomeMessageShown(shown: boolean): void {
    localStorage.setItem(this.hasSeenWelcomeMessageKey, JSON.stringify(shown));
  }

  hasShownWelcomeMessage(): boolean {
    return JSON.parse(localStorage.getItem(this.hasSeenWelcomeMessageKey) || 'false');
  }
}
