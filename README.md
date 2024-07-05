# ing-account-management

# Porject Name : ING Account Management Service

# Swagger URL : http://localhost:8080/swagger-ui/index.html

# Accounts API Documentation

# Base URL  http://localhost:8080/api/accounts

Endpoints Details 

# GET All Accounts

Example URL : http://localhost:8080/api/accounts

Description: Retrieves all accounts.

Response: Returns a list of all accounts with their details.

Sample Response:

{
  "success": true,
  "message": "Accounts retrieved successfully",
  "data": {
  "id": "0b5d9f2f-23fb-4fb3-b9b8-1e22c9b24451",
    "type": "SAVINGS",
    "openingDate": "2024-11-11",
    "temporary": false,
    "closureDate": null,
    "initialDeposit": 1000.0,
    "holder": {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "dateOfBirth": "1990-09-01",
      "email": "john.doe@example.com"
  }
}


# GET By UUID

Example URL : http://localhost:8080/api/accounts/0b5d9f2f-23fb-4fb3-b9b8-1e22c9b24451

Description: Retrieves an account by its ID.

Path Parameters: {id} UUID of the account to retrieve.

Response: Returns details of the account corresponding to the provided ID.

Sample Response:

{
  "success": true,
  "message": "Account retrieved successfully",
  "data": {
    "id": "0b5d9f2f-23fb-4fb3-b9b8-1e22c9b24451",
    "type": "SAVINGS",
    "openingDate": "2024-11-01",
    "temporary": false,
    "closureDate": null,
    "initialDeposit": 1000.0,
    "holder": {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "dateOfBirth": "1990-01-01",
      "email": "john.doe@example.com"
    }
  }
}

# POST /api/accounts/create

Example URL:  http://localhost:8080/api/accounts/create

Description: Creates a new account.

Request Body: AccountDto object representing the account details.

Sample Request Body:

{
  "type": "SAVINGS",
  "openingDate": "2024-11-20",
  "temporary": false,
  "closureDate": null,
  "initialDeposit": 1500.0,
  "holder": {
    "firstName": "Alice",
    "lastName": "Johnson",
    "dateOfBirth": "1988-09-10",
    "email": "alice.johnson@example.com"
  }
}

Response: Returns the created account details.

Sample Response:

{
  "success": true,
  "message": "Account created successfully",
  "data": {
    "id": "a56f9f21-ee5d-4e6b-a70e-7a2142b44c3b",
    "type": "SAVINGS",
    "openingDate": "2024-11-20",
    "temporary": false,
    "closureDate": null,
    "initialDeposit": 1500.0,
    "holder": {
      "id": 3,
      "firstName": "Alice",
      "lastName": "Johnson",
      "dateOfBirth": "1988-09-10",
      "email": "alice.johnson@example.com"
    }
  }
}

# PUT /api/accounts/{id}

Description: Updates an existing account.

Path Parameters:

{id}: UUID of the account to update.

Request Body: UpdateAccountDto object representing the updated account details.

Sample Request Body:


{
  "type": "CHECKING",
  "openingDate": "2024-11-20",
  "temporary": true,
  "closureDate": "2026-12-31",
  "initialDeposit": 2000.0,
  "holder": {
    "firstName": "Alice",
    "lastName": "Johnson",
    "dateOfBirth": "1988-09-10",
    "email": "alice.johnson@example.com"
  }
}

Response: Returns the updated account details.

Sample Response:

{
  "success": true,
  "message": "Account updated successfully",
  "data": {
    "id": "a56f9f21-ee5d-4e6b-a70e-7a2142b44c3b",
    "type": "CHECKING",
    "openingDate": "2024-11-20",
    "temporary": true,
    "closureDate": "2027-12-31",
    "initialDeposit": 2000.0,
    "holder": {
      "id": 3,
      "firstName": "Alice",
      "lastName": "Johnson",
      "dateOfBirth": "1988-09-10",
      "email": "alice.johnson@example.com"
    }
  }
}


# DELETE /api/accounts/{id}

Description: Deletes an account by its ID.

Path Parameters: {id} UUID of the account to delete.

Response: Returns a success message upon successful deletion.

Sample Response:

{
  "success": true,
  "message": "Account deleted successfully"
}




# Use the following JSON data for testing the API operations:

{
    "id": "a56f9f21-ee5d-4e6b-a70e-7a2142b44c3b",
    "type": "CHECKING",
    "openingDate": "2024-11-20",
    "temporary": true,
    "closureDate": "2027-12-31",
    "initialDeposit": 2000.0,
    "holder": {
      "id": 3,
      "firstName": "Alice",
      "lastName": "Johnson",
      "dateOfBirth": "1988-09-10",
      "email": "alice.johnson@example.com"
    }
  }
