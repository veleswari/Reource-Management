// src/app/models/user.model.ts

export interface User {
  id: number;
  fullName: string;
  email: string;
  password?: string;  // Optional, depending on whether you need to send it to the frontend
}
