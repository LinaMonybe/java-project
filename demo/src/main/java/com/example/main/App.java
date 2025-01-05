package com.example.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the Main FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml"));
            Parent root = loader.load();

            // Set the scene with the loaded FXML
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // Set the title of the application window
            primaryStage.setTitle("Main Menu");

            // Show the stage (the application window)
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}




// package com.example.main;

// import java.util.List;
// import java.util.Scanner;

// import com.example.dao.ReservationDAO;
// import com.example.dao.SalleDAO;
// import com.example.dao.TerrainDAO;
// import com.example.dao.UtilisateurDAO;
// import com.example.model.Reservation;
// import com.example.model.Salle;
// import com.example.model.Terrain;
// import com.example.model.Utilisateur;

// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;

// public class App extends Application {

//     public static void main(String[] args) {
//         System.out.println("Choose mode: 1 for GUI, 2 for CLI");
//         Scanner sc = new Scanner(System.in);
//         int mode = sc.nextInt();
//         sc.nextLine();
//         if (mode == 1) {
//             launch(args); // Launch JavaFX
//         } else if (mode == 2) {
//             startCLI(); // Start CLI mode
//         } else {
//             System.out.println("Invalid choice. Exiting.");
//         }
//     }

//     @Override
//     public void start(Stage primaryStage) throws Exception {
//         // Load the MainView.fxml file
//         Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
//         primaryStage.setTitle("Management Application");
//         primaryStage.setScene(new Scene(root));
//         primaryStage.show();
//     }

//     // Retaining original CLI code for compatibility
//     public static void startCLI() {
//         System.out.println("Hello there! Welcome and please choose an option:");
//         System.out.println("1. Manage Users");
//         System.out.println("2. Manage Rooms (Salle)");
//         System.out.println("3. Manage Fields (Terrain)");
//         System.out.println("4. Manage Reservations");
//         System.out.println("5. Exit");

//         Scanner sc = new Scanner(System.in);
//         int choice = sc.nextInt();
//         sc.nextLine(); // Consume the newline character

//         while (choice != 5) {
//             switch (choice) {
//                 case 1:
//                     manageUsers(sc);
//                     break;

//                 case 2:
//                     manageSalle(sc);
//                     break;

//                 case 3:
//                     manageTerrain(sc);
//                     break;

//                 case 4:
//                     manageReservation(sc);
//                     break;

//                 default:
//                     System.out.println("Invalid choice, please try again.");
//                     break;
//             }

//             // Re-display the main menu
//             System.out.println("Please choose an option:");
//             System.out.println("1. Manage Users");
//             System.out.println("2. Manage Rooms (Salle)");
//             System.out.println("3. Manage Fields (Terrain)");
//             System.out.println("4. Manage Reservations");
//             System.out.println("5. Exit");
//             choice = sc.nextInt();
//             sc.nextLine(); // Consume the newline character
//         }

//         sc.close();
//         System.out.println("BYE!");
//     }

//     private static void manageUsers(Scanner sc) {
//         System.out.println("User Management:");
//         System.out.println("1. Create a new user");
//         System.out.println("2. Delete a user");
//         System.out.println("3. Update a user");
//         System.out.println("4. Display all users");

//         int userChoice = sc.nextInt();
//         sc.nextLine(); // Consume the newline character

//         UtilisateurDAO userdao = new UtilisateurDAO();

//         switch (userChoice) {
//             case 1:
//                 System.out.println("Enter firstname: ");
//                 String firstname = sc.nextLine();
//                 System.out.println("Enter lastname: ");
//                 String lastname = sc.nextLine();
//                 System.out.println("Enter email: ");
//                 String email = sc.nextLine();
//                 System.out.println("Are you a (Student or Professor): ");
//                 String type = sc.nextLine();
//                 Utilisateur user = new Utilisateur(firstname, lastname, email, type);
//                 userdao.add(user);
//                 break;

//             case 2:
//                 System.out.println("Enter user ID to delete: ");
//                 int deleteId = sc.nextInt();
//                 sc.nextLine(); // Consume newline
//                 userdao.delete(deleteId);
//                 break;

//             case 3:
//                 System.out.println("Enter user ID to update: ");
//                 int updateId = sc.nextInt();
//                 sc.nextLine();
//                 System.out.println("Enter new firstname: ");
//                 String newFirstName = sc.nextLine();
//                 System.out.println("Enter new lastname: ");
//                 String newLastName = sc.nextLine();
//                 System.out.println("Enter new email: ");
//                 String newEmail = sc.nextLine();
//                 System.out.println("Enter new type (Student or Professor): ");
//                 String newType = sc.nextLine();
//                 Utilisateur updatedUser = new Utilisateur(updateId, newFirstName, newLastName, newEmail, newType);
//                 userdao.update(updatedUser);
//                 break;

//             case 4:
//                 System.out.println("Displaying all users:");
//                 List<Utilisateur> list = userdao.getAll();
//                 for (Utilisateur x : list) {
//                     System.out.println(x);
//                 }
//                 break;

//             default:
//                 System.out.println("Invalid choice, please try again.");
//                 break;
//         }
//     }

//     private static void manageSalle(Scanner sc) {
//         System.out.println("Room Management:");
//         System.out.println("1. Create a new room");
//         System.out.println("2. Delete a room");
//         System.out.println("3. Update a room");
//         System.out.println("4. Display all rooms");

//         int salleChoice = sc.nextInt();
//         sc.nextLine(); // Consume the newline character
//         SalleDAO salleDAO = new SalleDAO();

//         switch (salleChoice) {
//             case 1:
//                 System.out.println("Enter room ID: ");
//                 int roomId = sc.nextInt();
//                 sc.nextLine(); // Consume the newline character
//                 System.out.println("Enter room name: ");
//                 String roomName = sc.nextLine();
//                 System.out.println("Enter room capacity: ");
//                 int capacity = sc.nextInt();
//                 Salle newSalle = new Salle(roomId, roomName, capacity);
//                 salleDAO.add(newSalle);
//                 break;

//             case 2:
//                 System.out.println("Enter room ID to delete: ");
//                 int roomIdToDelete = sc.nextInt();
//                 salleDAO.delete(roomIdToDelete);
//                 break;

//             case 3:
//                 System.out.println("Enter room ID to update: ");
//                 int roomIdToUpdate = sc.nextInt();
//                 sc.nextLine(); // Consume the newline character
//                 System.out.println("Enter new room name: ");
//                 String newRoomName = sc.nextLine();
//                 System.out.println("Enter new room capacity: ");
//                 int newCapacity = sc.nextInt();
//                 Salle updatedSalle = new Salle(roomIdToUpdate, newRoomName, newCapacity);
//                 salleDAO.update(updatedSalle);
//                 break;

//             case 4:
//                 System.out.println("Displaying all rooms:");
//                 List<Salle> salles = salleDAO.getAll();
//                 for (Salle salle : salles) {
//                     System.out.println(salle);
//                 }
//                 break;

//             default:
//                 System.out.println("Invalid choice, please try again.");
//                 break;
//         }
//     }

//     private static void manageTerrain(Scanner sc) {
//         System.out.println("Field Management:");
//         System.out.println("1. Create a new field");
//         System.out.println("2. Delete a field");
//         System.out.println("3. Update a field");
//         System.out.println("4. Display all fields");

//         int terrainChoice = sc.nextInt();
//         sc.nextLine(); // Consume the newline character
//         TerrainDAO terrainDAO = new TerrainDAO();

//         switch (terrainChoice) {
//             case 1:
//                 System.out.println("Enter field ID: ");
//                 int fieldId = sc.nextInt();
//                 sc.nextLine(); // Consume the newline character
//                 System.out.println("Enter field name: ");
//                 String fieldName = sc.nextLine();
//                 System.out.println("Enter field type: ");
//                 String fieldType = sc.nextLine();
//                 Terrain newTerrain = new Terrain(fieldId, fieldName, fieldType);
//                 terrainDAO.add(newTerrain);
//                 break;

//             case 2:
//                 System.out.println("Enter field ID to delete: ");
//                 int fieldIdToDelete = sc.nextInt();
//                 terrainDAO.delete(fieldIdToDelete);
//                 break;

//             case 3:
//                 System.out.println("Enter field ID to update: ");
//                 int fieldIdToUpdate = sc.nextInt();
//                 sc.nextLine(); // Consume the newline character
//                 System.out.println("Enter new field name: ");
//                 String newFieldName = sc.nextLine();
//                 System.out.println("Enter new field type: ");
//                 String newFieldType = sc.nextLine();
//                 Terrain updatedTerrain = new Terrain(fieldIdToUpdate, newFieldName, newFieldType);
//                 terrainDAO.update(updatedTerrain);
//                 break;

//             case 4:
//                 System.out.println("Displaying all fields:");
//                 List<Terrain> terrains = terrainDAO.getAll();
//                 for (Terrain terrain : terrains) {
//                     System.out.println(terrain);
//                 }
//                 break;

//             default:
//                 System.out.println("Invalid choice, please try again.");
//                 break;
//         }
//     }

//     private static void manageReservation(Scanner sc) {
//         System.out.println("Reservation Management:");
//         System.out.println("1. Create a new reservation");
//         System.out.println("2. Delete a reservation");
//         System.out.println("3. Update a reservation");
//         System.out.println("4. Display all reservations");

//         int reservationChoice = sc.nextInt();
//         sc.nextLine(); // Consume the newline character
//         ReservationDAO reservationDAO = new ReservationDAO();

//         switch (reservationChoice) {
//             case 1:
//                 System.out.println("Enter reservation ID: ");
//                 int reservationId = sc.nextInt();
//                 sc.nextLine(); // Consume the newline character
//                 System.out.println("Enter user ID: ");
//                 int userId = sc.nextInt();
//                 sc.nextLine();
//                 System.out.println("Enter event ID: ");
//                 int eventid = sc.nextInt();
//                 sc.nextLine();
//                 System.out.println("Enter room ID: ");
//                 int roomId = sc.nextInt();
//                 System.out.println("Enter field ID: ");
//                 int fieldId = sc.nextInt();
//                 System.out.println("Enter reservation date: ");
//                 String date = sc.nextLine();
//                 Reservation newReservation = new Reservation(reservationId, userId,eventid, roomId, date);
//                 reservationDAO.add(newReservation);
//                 break;

//             case 2:
//                 System.out.println("Enter reservation ID to delete: ");
//                 int reservationIdToDelete = sc.nextInt();
//                 reservationDAO.delete(reservationIdToDelete);
//                 break;

//                 case 3:
//                 System.out.println("Enter reservation ID to update: ");
//                 int reservationIdToUpdate = sc.nextInt();
//                 sc.nextLine(); // Consume the newline character after nextInt()
                
//                 System.out.println("Enter new user ID: ");
//                 int newUserId = sc.nextInt();
//                 sc.nextLine(); // Consume the newline character after nextInt()
                
//                 System.out.println("Enter new event ID: ");
//                 int idevent2 = sc.nextInt();
//                 sc.nextLine(); // Consume the newline character after nextInt()
                
//                 System.out.println("Enter new room ID: ");
//                 int newRoomId = sc.nextInt();
//                 sc.nextLine(); // Consume the newline character after nextInt()
                
//                 System.out.println("Enter new field ID: ");
//                 int newFieldId = sc.nextInt();
//                 sc.nextLine(); // Consume the newline character after nextInt()
            
//                 // Read the new date input
//                 System.out.println("Enter new reservation date (yyyy-MM-dd): ");
//                 String newDate = sc.nextLine();  // Now this will work correctly
            
//                 // Create the updated reservation object
//                // Create the updated reservation object with the parsed Date
// Reservation updatedReservation = new Reservation(reservationIdToUpdate, newUserId, idevent2, newRoomId, newDate);
//                 // Update the reservation in the database
//                 reservationDAO.update(updatedReservation);
//                 break;
            

//             case 4:
//                 System.out.println("Displaying all reservations:");
//                 List<Reservation> reservations = reservationDAO.getAll();
//                 for (Reservation reservation : reservations) {
//                     System.out.println(reservation);
//                 }
//                 break;

//             default:
//                 System.out.println("Invalid choice, please try again.");
//                 break;
//         }
//     }
// }
