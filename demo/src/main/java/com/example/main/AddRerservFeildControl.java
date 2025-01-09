package com.example.main;
import java.io.IOException;
import java.util.List;

import com.example.dao.EvenementDAO;
import com.example.dao.ReservationDAO;
import com.example.dao.UtilisateurDAO;
import com.example.model.Evenement;
import com.example.model.Reservation;
import com.example.model.Terrain;
import com.example.model.Utilisateur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddRerservFeildControl {
    @FXML
    private Button BackToRoom;

    @FXML
    private Button EventAdd;

    @FXML
    private DatePicker EventDate;

    @FXML
    private Button EventList;

    @FXML
    private TextField EventName;

    @FXML
    private MenuButton EventSelect;

    @FXML
    private DatePicker ReserveDate;

    @FXML
    private MenuButton UsersS;

    @FXML
    private TextArea decription;

    @FXML
    private Button reserve;

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private final ObservableList<Utilisateur> userList = FXCollections.observableArrayList();
    private Utilisateur selectedUser = null;

   private final EvenementDAO evenementDAO = new EvenementDAO();
    private final ObservableList<Evenement> eventList = FXCollections.observableArrayList();
    private Evenement selectedEvent = null;

    Terrain selectedfeild = null;

    @FXML
    public void initialize() {
        loadUserData();
        loadEventData();
    }

    private void loadEventData() {
        List<Evenement> events = evenementDAO.getAll();
        EventSelect.getItems().clear();
        eventList.setAll(events);
        for (Evenement event : eventList) {
            MenuItem eventMenuItem = new MenuItem(event.getNomEvent());
            eventMenuItem.setOnAction(eventAction -> {
                selectedEvent = event;
                System.out.println("Selected Event: " + selectedEvent.getIdEvent() + " - " + selectedEvent.getNomEvent());
            });
            EventSelect.getItems().add(eventMenuItem);
        }
    }
    

    private void loadUserData() {
        List<Utilisateur> users = utilisateurDAO.getAll();
        UsersS.getItems().clear();
        userList.setAll(users);
        for (Utilisateur user : userList) {
            MenuItem userMenuItem = new MenuItem(user.getNom());
            userMenuItem.setOnAction(userEvent -> selectedUser = user);
            UsersS.getItems().add(userMenuItem);
        }
    }

    @FXML
    void Back(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/terrain.fxml");
    }

    @FXML
    void BackToList(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/event.fxml");
    }

    @FXML
    void BackToUserList(ActionEvent event)throws IOException {
        switchScene(event, "/com/example/user.fxml");
    }
   public void setfeild(Terrain terrain) {
        this.selectedfeild = terrain;
    }
    @FXML
    void addnewReseve(ActionEvent event) {
        try {
            if (selectedUser == null || selectedEvent == null || selectedfeild == null || ReserveDate.getValue() == null) {
                System.out.println("Please ensure all fields are selected");
                return;
            }
            int userId = selectedUser.getId();
            int eventId = selectedEvent.getIdEvent();
            int fieldId = selectedfeild.getIdTerrain();
            String reservationDate = ReserveDate.getValue().toString();
            System.out.println("Event ID: " + eventId);
            
            Reservation newReservation = new Reservation(0, userId, eventId, 0, fieldId, reservationDate);
            ReservationDAO reservationDAO = new ReservationDAO();
            reservationDAO.add(newReservation);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
