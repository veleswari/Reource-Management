import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginData = {
    email: '',
    password: ''
  };

  constructor(
    private authService: AuthService,
    private router: Router,
    private dialogRef: MatDialogRef<LoginComponent>
  ) {}

  ngOnInit(): void {}

  onSubmit(): void {
    const { email, password } = this.loginData;
    this.authService.login(email, password).subscribe(
      () => {
        const fullName = this.authService.getFullName();
        console.log(`Welcome, ${fullName}!`); // Log the full name
        this.dialogRef.close({ authenticated: true });
        this.router.navigate(['/main-page']); // Navigate to main page after successful login
      },
      (error: any) => {
        console.error('Login failed:', error);
        // Optionally, handle login failure (show error message, etc.)
      }
    );
  }
}
