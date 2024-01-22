# NotesAPI
Checking a Validation 

## Spring Security

### Authentication

- It's process where the user gets authenticated to use set of features of an application for entitled roles.

### Authorization

- It's a process where the user gets verified against the datastore to use a request resource of the application for entitled roles.

### Principal

- It's the authenticated and authorized user of the application.

### Granted Role

- Role is defined as a set of permission a user can do on the application he's authorized.

#### Authentication v/s Authorization

- Authentication is a first to ensure if the user is part of the system. It's generally validated when user tries to login to the system filling the login form on the application.
- Authorization is done based on simultaneous requests to the server if the user is present or not in the database.

- Login/Password (Only once these credentials are give) -> Authentication -> Response (JWT token) to the client 
- In every request this JWT token is sent -> Backend decodes the JWT (id, email) and same is verified on the backend -> If user is present we allow the requested resource to continue, or we throw UnAuthorisedException. 