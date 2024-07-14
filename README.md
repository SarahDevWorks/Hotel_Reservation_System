# Hotel Reservation System

## Overview

The Hotel Reservation System is a Java-based application designed to manage hotel reservations efficiently. This system allows users to reserve rooms, view existing reservations, get room numbers based on reservation details, update reservations, and delete reservations. The application connects to a MySQL database to store and retrieve reservation data.

## Features

- **Reserve a Room**: Allows users to reserve a room by entering guest details.
- **View Reservations**: Displays all current reservations in the system.
- **Get Room Number**: Retrieves the room number based on reservation ID and guest name.
- **Update Reservation**: Updates the details of an existing reservation.
- **Delete Reservation**: Deletes an existing reservation from the system.

## Requirements

- Java Development Kit (JDK) 8 or higher
- MySQL Database
- MySQL JDBC Driver

## Setup

### MySQL Database

1. Install MySQL and MySQL Workbench if not already installed.
2. Create a database named `hotel_db`.
3. Create a table named `reservations` with the following structure:

```sql
CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(50),
    room_number INT,
    contact_number VARCHAR(15),
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Java Project

1. Clone this repository or download the source code.
2. Import the project into your preferred Java IDE (Eclipse, IntelliJ, etc.).
3. Add the MySQL JDBC Driver to your project classpath. You can download it from [here](https://dev.mysql.com/downloads/connector/j/).
4. Update the database connection details in the `HotelReservationSystem` class if necessary.

```java
private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
private static final String user = "root";
private static final String pass = "";
```

## Usage

1. Run the `HotelReservationSystem` class.
2. Follow the on-screen instructions to perform various operations like reserving a room, viewing reservations, getting room numbers, updating, and deleting reservations.

## Code Structure

- **HotelReservationSystem.java**: Contains the main class and all the methods for different operations (reserve, view, update, delete).
- **reserveRoom()**: Method to handle room reservation.
- **viewReservations()**: Method to display all current reservations.
- **getRoomNumber()**: Method to get the room number based on reservation ID and guest name.
- **updateReservation()**: Method to update an existing reservation.
- **deleteReservation()**: Method to delete an existing reservation.
- **reservationExists()**: Helper method to check if a reservation exists.
- **exit()**: Method to exit the application gracefully.

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests for any improvements or bug fixes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or suggestions, feel free to reach out to me via LinkedIn or email.

---

Thank you for checking out the Hotel Reservation System! Your feedback and suggestions are highly appreciated. 😊
