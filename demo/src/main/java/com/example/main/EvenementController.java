package com.example.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.dao.EvenementDAO;
import com.example.dao.UtilisateurDAO;
import com.example.model.Evenement;
import com.example.model.Utilisateur;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EvenementController {
    @FXML
    private TextArea DEscIn;

    @FXML
    private DatePicker DateIn;

    @FXML
    private TextField EventName;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Evenement, String> colDesc;

    @FXML
    private TableColumn<Evenement, String> colName;

    @FXML
    private TableColumn<Evenement, String> Date;

    @FXML
    private TableView<Evenement> Table;

    @FXML
    private TableColumn<Evenement, String> User;

    @FXML
    private TableColumn<Evenement, String> colId;

    @FXML
    private MenuButton UserName;

    private EvenementDAO evenementDAO = new EvenementDAO();
    private ObservableList<Evenement> evenementList = FXCollections.observableArrayList();
    private Evenement selectedEvenement = null;
    private Map<String, Integer> userMap = new HashMap<>();

    @FXML
private void initialize() {
    colId.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
    colName.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
    Date.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
    colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
    
   
    User.setCellValueFactory(param -> {
        int userId = param.getValue().getIdUser();
        String userName = getUserFullName(userId);
        return new SimpleStringProperty(userName);
    });

    loadEvenementData();
    loadUsers();

    Table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            selectedEvenement = newSelection;
            fillForm(selectedEvenement);
        }
    });
}

private String getUserFullName(int userId) {
    UtilisateurDAO userDAO = new UtilisateurDAO();
    Utilisateur user = userDAO.get(userId);
    return user != null ? user.getNom() + " " + user.getPrenom() : "";
}
    private void loadUsers() {
        UtilisateurDAO userDAO = new UtilisateurDAO();
        List<Utilisateur> users = userDAO.getAll();

        userMap.clear();
        UserName.getItems().clear();

        for (Utilisateur user : users) {
            MenuItem menuItem = new MenuItem(user.getNom() + " " + user.getPrenom());
            userMap.put(user.getNom() + " " + user.getPrenom(), user.getId());

            menuItem.setOnAction(event -> {
                UserName.setText(menuItem.getText());
            });

            UserName.getItems().add(menuItem);
        }
    }

    private void loadEvenementData() {
        evenementList.clear();
        evenementList.addAll(evenementDAO.getAll());
        Table.setItems(evenementList);
    }

    private void fillForm(Evenement evenement) {
        EventName.setText(evenement.getNomEvent());
        DateIn.setValue(java.time.LocalDate.parse(evenement.getDateEvent()));
        DEscIn.setText(evenement.getDescription());
        UserName.setText(getUserFullName(evenement.getIdUser()));
    }

    @FXML
private void handleSave() {
    String name = EventName.getText();
    String date = DateIn.getValue() != null ? DateIn.getValue().toString() : "";
    String description = DEscIn.getText();
    Integer userId = userMap.get(UserName.getText());

    if (name.isEmpty() || date.isEmpty() || description.isEmpty()) {
        showAlert(AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
        return;
    }

    if (userId == null) { 
        showAlert(AlertType.ERROR, "Erreur", "Utilisateur non valide. Veuillez sélectionner un utilisateur.");
        return;
    }

    java.sql.Date sqlDate = java.sql.Date.valueOf(date);
    Evenement evenement = new Evenement(0, name, sqlDate.toString(), description, userId);
    evenementDAO.add(evenement);
    loadEvenementData();
    clearForm();
}

@FXML
private void handleUpdate() {
    if (selectedEvenement == null) {
        showAlert(AlertType.WARNING, "Avertissement", "Veuillez sélectionner un événement à modifier.");
        return;
    }

    Integer userId = userMap.get(UserName.getText());

    if (userId == null) {
        showAlert(AlertType.ERROR, "Erreur", "Utilisateur non valide. Veuillez sélectionner un utilisateur.");
        return;
    }

    selectedEvenement.setNomEvent(EventName.getText());
    selectedEvenement.setDateEvent(DateIn.getValue().toString());
    selectedEvenement.setDescription(DEscIn.getText());
    selectedEvenement.setIdUser(userId);

    evenementDAO.update(selectedEvenement);
    loadEvenementData();
    clearForm();
}

    @FXML
    private void handleDelete(ActionEvent event) {
        if (selectedEvenement == null) {
            showAlert(AlertType.WARNING, "Avertissement", "Veuillez sélectionner un événement à supprimer.");
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer l'événement");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            evenementDAO.delete(selectedEvenement.getIdEvent());
            loadEvenementData();
            clearForm();
        }
    }

    @FXML
    public void Back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml")); 
            Parent root = loader.load();
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        clearForm();
    }

    @FXML
    void UserSelect(ActionEvent event) {
        if (UserName.getText().isEmpty() || !userMap.containsKey(UserName.getText())) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner un utilisateur valide.");
        }
    }

    private void clearForm() {
        EventName.clear();
        DateIn.setValue(null);
        DEscIn.clear();
        UserName.setText("");
        selectedEvenement = null;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
