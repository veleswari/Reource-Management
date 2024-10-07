import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { AuthService } from './services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authToken = this.authService.getToken();

    if (authToken) {
      const authReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${authToken}`)
      });

      return next.handle(authReq).pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 401) {
            console.error('JWT token expired error:', error.error.message);

            // Attempt to refresh token
            return this.authService.refreshToken().pipe(
              switchMap(() => {
                // Retry the original request with the new token
                const updatedAuthReq = req.clone({
                  headers: req.headers.set('Authorization', `Bearer ${this.authService.getToken()}`)
                });
                return next.handle(updatedAuthReq);
              }),
              catchError(refreshError => {
                // Handle refresh token error (e.g., logout user)
                console.error('Failed to refresh token:', refreshError);
                this.authService.logout();
                return throwError(refreshError);
              })
            );
          }
          return throwError(error);
        })
      );
    } else {
      return next.handle(req);
    }
  }
}
