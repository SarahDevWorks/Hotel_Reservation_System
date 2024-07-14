import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {

    // Database connection details
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String user = "root";
    private static final String pass = "";

    public static void main(String[] args) {

        try {
            // Load and register JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            // Establish the connection to the database
            Connection conn = DriverManager.getConnection(url, user, pass);

            // Loop for the main menu
            while(true) {
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                System.out.println("1. Reserve A Room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.println("-----------------------------");
                System.out.print("Enter Your Operation -> ");

                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();

                // Handle menu choices
                switch (choice) {
                    case 1:
                        reserveRoom(conn, sc);
                        break;
                    case 2:
                        viewReservations(conn);
                        break;
                    case 3:
                        getRoomNumber(conn, sc);
                        break;
                    case 4:
                        updateReservation(conn, sc);
                        break;
                    case 5:
                        deleteReservation(conn, sc);
                        break;
                    case 0:
                        exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice, try again...");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to reserve a room
    private static void reserveRoom(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter guest name: ");
            String guestName = sc.next();

            System.out.print("Enter room number: ");
            int roomNo = sc.nextInt();

            System.out.print("Enter contact number: ");
            String contactNum = sc.next();

            String query = "INSERT INTO reservations(guest_name, room_number, contact_number) " +
                    "VALUES ('"+guestName+"','"+roomNo+"','"+contactNum+"')";

            Statement st = conn.createStatement();
            int res = st.executeUpdate(query);

            if(res > 0) {
                System.out.println("Reservation successful...");
            } else {
                System.out.println("Reservation failed...");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to view all reservations
    private static void viewReservations(Connection conn) {
        try {
            String query = "SELECT * FROM reservations";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("Current Reservation:");
            System.out.println("+--------------------+--------------------+--------------------+--------------------+---------------------+");
            System.out.println("|   Reservation ID   |     Guest Name     |     Room Number    |   Contact Number   |  Reservation date   |");
            System.out.println("+--------------------+--------------------+--------------------+--------------------+---------------------+");

            while (rs.next()) {
                int reservationId = rs.getInt("reservation_id");
                String guestName = rs.getString("guest_name");
                int roomNo = rs.getInt("room_number");
                String contactNum = rs.getString("contact_number");
                String reservationDate = rs.getString("reservation_date");

                System.out.printf("|%19d |%19s |%19d |%19s |%20s |%n", reservationId, guestName, roomNo, contactNum, reservationDate);
            }
            System.out.println("+--------------------+--------------------+--------------------+--------------------+---------------------+");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to get room number based on reservation ID and guest name
    private static void getRoomNumber(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter reservation id: ");
            int reservationId = sc.nextInt();
            System.out.print("Enter guest name: ");
            String guestName = sc.next();

            String query = "SELECT room_number FROM reservations " +
                    "WHERE reservation_id = "+ reservationId +" AND guest_name = '"+guestName+"'";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if(rs.next()) {
                int roomNumber = rs.getInt("room_number");
                System.out.println("Room number for Guest - "+guestName+" is :"+roomNumber);
            } else {
                System.out.println("Reservation not found for the given ID & Guest name");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to update a reservation
    private static void updateReservation(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter reservation id to update: ");
            int reservationId = sc.nextInt();

            if(!reservationExists(conn, reservationId)){
                System.out.println("Reservation not found for the give ID");
                return;
            }

            System.out.print("Enter guest name: ");
            String newGuestName = sc.next();

            System.out.print("Enter room number: ");
            int newRoomNo = sc.nextInt();

            System.out.print("Enter contact number: ");
            String newContactNum = sc.next();

            String query = "UPDATE reservations SET guest_name = '"+ newGuestName +"', room_number = "+ newRoomNo +", " +
                    "contact_number = '"+ newContactNum +"' WHERE reservation_id = "+reservationId;

            Statement st = conn.createStatement();
            int res = st.executeUpdate(query);

            if(res > 0) {
                System.out.println("Reservation updated successfully...");
            } else {
                System.out.println("Reservation update failed...");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to delete a reservation
    private static void deleteReservation(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter reservation id to update: ");
            int reservationId = sc.nextInt();

            if(!reservationExists(conn, reservationId)){
                System.out.println("Reservation not found for the give ID");
                return;
            }

            String query = "DELETE FROM reservations WHERE reservation_id = "+reservationId;

            Statement st = conn.createStatement();
            int res = st.executeUpdate(query);

            if(res > 0) {
                System.out.println("Reservation deleted successfully...");
            } else {
                System.out.println("Reservation deletion failed...");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to check if a reservation exists
    private static boolean reservationExists(Connection conn, int reservationId) {
        try {
            String query = "SELECT reservation_id FROM reservations WHERE reservation_id =" + reservationId;

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Method to exit the system
    private static void exit() throws InterruptedException{
        System.out.print("Existing System");
        int i = 3;
        while (i!=0) {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank You For Using Hotel Reservation System!!!");
    }
}
