import { Injectable } from '@angular/core';
import { User } from '../user.model'; // Replace with your actual user model if exists

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private currentUser: User | null = null;
  private userKey = 'currentUser'; // Key for storing user object in localStorage

  constructor() {
    // Initialize currentUser from localStorage if available
    this.loadUserFromLocalStorage();
  }

  private loadUserFromLocalStorage(): void {
    const storedUser = localStorage.getItem(this.userKey);
    if (storedUser) {
      try {
        this.currentUser = JSON.parse(storedUser) as User;
      } catch (error) {
        console.error('Error parsing stored user:', error);
        this.currentUser = null; // Reset currentUser in case of parsing error
        localStorage.removeItem(this.userKey); // Remove invalid data from localStorage
      }
    }
  }

  setCurrentUser(user: User): void {
    this.currentUser = user;
    localStorage.setItem(this.userKey, JSON.stringify(user)); // Store user in localStorage for persistence
  }

  getCurrentUser(): User | null {
    return this.currentUser;
  }

  isLoggedIn(): boolean {
    return this.getCurrentUser() !== null;
  }

  logout(): void {
    this.currentUser = null;
    localStorage.removeItem(this.userKey); // Remove user from localStorage on logout
  }
}
