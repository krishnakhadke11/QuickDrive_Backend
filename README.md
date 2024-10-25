# Transportation Service Application

## Requirements
- Java >= 22
- MySQL

## Setup Instructions

### 1. Create a .env File
Create a file named `.env` in the `src/main/resources` directory with the following format:

```plaintext
  DB_URL=jdbc:mysql://localhost:3306/transportation_service
  DB_USERNAME=
  DB_PASSWORD=
  
  JWT_SECRET_KEY=
  JWT_EXPIRATION_TIME=3600000
  JWT_REFRESH_TOKEN_EXPIRATION_TIME=86400000
  
  GOOGLE_MAPS_API_KEY=
```
# Application Setup Instructions

This document provides instructions for configuring and running the Transportation Service application.

## 1. Configure Environment Variables

1. Navigate to `src/main/java/TransportationServiceApplication`.
2. Right-click on the `TransportationServiceApplication` file.
3. Click on **"Modify Run Configuration"**.
4. Add the `.env` file to the **Environment Variables** section.

## 2. Create the Database

Before running the application, you need to create the `transportation_service` database in MySQL.

## 3. Running the Application

1. Go to `src/main/java/TransportationServiceApplication`.
2. Right-click anywhere in the file and select **"Run"** to start the application.

## Notes

- Ensure that your MySQL server is running and accessible.
- Fill in the values for `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET_KEY`, and `GOOGLE_MAPS_API_KEY` in the `.env` file as per your configuration.
