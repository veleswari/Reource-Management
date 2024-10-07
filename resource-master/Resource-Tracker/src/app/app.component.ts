import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  isLoggedIn = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    // Check initial login state when AppComponent initializes
    this.isLoggedIn = !!localStorage.getItem('isLoggedIn');
    if (!this.isLoggedIn) {
      this.router.navigate(['/overview']); // Navigate to overview if not logged in
    }
  }

  logout(): void {
    localStorage.removeItem('isLoggedIn');
    this.router.navigate(['/overview']);
  }
}
