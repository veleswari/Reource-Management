// environment.ts or environment.prod.ts

export const environment = {
  production: false, // or true in environment.prod.ts
  security: {
    jwt: {
      header: 'Authorization', // Replace with your actual header name
      prefix: 'Bearer' // Replace with your actual token prefix
    }
  }
  // Other environment variables as needed
};
