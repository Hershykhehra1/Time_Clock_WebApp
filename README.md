### Project Structure

The project is divided into several components:

#### Model:

- **UserEntry.java:** This class represents a user with fields for user ID, email, username, and password. It includes validation for required fields and methods for managing user data.

- **TimeClock.java:** This class represents a time clock entry with fields for user ID, clock-in time, and clock-out time. It includes validation for required fields and methods for managing time clock data.

#### Components:

- **UserRepository.java:** This component manages the database operations related to user data. It includes methods for adding, retrieving, and checking the existence of users.

- **TimeClockRepository.java:** This component manages the database operations related to time clock entries. It includes methods for adding, retrieving, and checking the existence of time clock entries.

#### Controllers:

- **ApiController.java:** This controller handles API requests for user registration, login, clocking in, and clocking out. It includes methods for processing user input, interacting with the repositories, and returning appropriate responses.

#### Templates:

- **main.ftlh:** A template for the main application interface. It includes forms for user registration, login, clocking in, and clocking out, along with validation and error messages.

#### Frontend:

- **main.js:** This JavaScript file manages the user interface interactions, including form submissions, displaying messages, and time clock entries. It uses jQuery for AJAX requests and DOM manipulation.

### How It Works:

1. **User Registration:** 
   - Users can navigate to the registration form, fill out their email, username, and password, and submit to create a new account.

2. **User Login:** 
   - Users can log in using their registered email and password. Successful login redirects them to the main interface, displaying their welcome message and time clock entries.

3. **Clocking In/Out:** 
   - Logged-in users can clock in and clock out using the respective buttons. The application records the timestamps and displays the entries in a table format.

4. **Viewing Time Clock Entries:**
   - The main interface displays all time clock entries for the logged-in user in a table format, showing the date, time, and action (clocked in/out).

### How to Run:

1. Make sure you have Java, Maven, and Spring installed in your IDE.
2. Run the Spring Boot app and navigate to http://localhost:8080/ to interact with the application.
