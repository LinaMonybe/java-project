package com.example.main;
import java.util.List;
import com.example.dao.EvenementDAO;
import com.example.dao.ReservationDAO;
import com.example.dao.UtilisateurDAO;
import com.example.model.Evenement;
import com.example.model.Reservation;
import com.example.model.Salle;
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
import java.time.LocalDate;
import java.io.IOException;

public class AddRsereveController {

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

    Salle selectedSalle = null;

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
            eventMenuItem.setOnAction(eventAction -> selectedEvent = event);
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
        switchScene(event, "/com/example/salle.fxml");
    }

    @FXML
    void BackToList(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/event.fxml");
    }

    @FXML
    void BackToUserList(ActionEvent event)throws IOException {
        switchScene(event, "/com/example/user.fxml");
    }
   public void setSalle(Salle salle) {
        this.selectedSalle = salle;
    }
    @FXML
    void addnewReseve(ActionEvent event) {
       try {
        if (selectedUser == null || selectedEvent == null || selectedSalle == null || ReserveDate.getValue() == null) {
            return;
        }
        int userId = selectedUser.getId();
        int eventId = selectedEvent.getIdEvent();
        int salleId = selectedSalle.getIdSalle();
        String reservationDate = ReserveDate.getValue().toString();

        Reservation newReservation = new Reservation(0, userId, eventId, salleId, 0, reservationDate);
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
