package com.example.main;

import java.io.IOException;

import com.example.dao.UtilisateurDAO;
import com.example.model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignController {

    @FXML
    private TextField type;

    @FXML
    private Button btnValid;

    @FXML
    private TextField email;

    @FXML
    private TextField lastName;

    @FXML
    private Hyperlink linkLogin;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    void ValidClick(ActionEvent event) {
        try {
            String userEmail = email.getText().trim();
            String userPassword = password.getText().trim();
            String userName = name.getText().trim();
            String userLastName = lastName.getText().trim();
            String userType = type.getText().trim().toLowerCase();

            if (userEmail.isEmpty() || userPassword.isEmpty() || userName.isEmpty() || userLastName.isEmpty() || userType.isEmpty()) {
                System.out.println("Veuillez remplir tous les champs.");
                return;
            }

            if (!userType.equals("student") && !userType.equals("professor")) {
                System.out.println("Le type doit être 'student' ou 'professor'.");
                return;
            }

            Utilisateur newUser = new Utilisateur(0, userName, userLastName, userEmail, userType, userPassword);
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            utilisateurDAO.add(newUser);

            System.out.println("Utilisateur enregistré avec succès !");
            backToLogin(event);

        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de l'enregistrement :");
            e.printStackTrace();
        }
    }

    @FXML
    void backToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Page de connexion");
        stage.show();
    }
}
