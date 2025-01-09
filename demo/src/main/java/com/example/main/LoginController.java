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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
 @FXML
    private Button btnLogin;

    @FXML
    private Button btnSign;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

   @FXML
void loginClick(ActionEvent event) {
    try {
        String userEmail = email.getText().trim();
        String userPassword = password.getText().trim();

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            System.out.println("Please enter your email and password.");
            return;
        }

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        Utilisateur user = utilisateurDAO.validateUser(userEmail, userPassword);

        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getNom() + " " + user.getPrenom());
            if(user.getType().equals("admin")){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Welcome Admin");
                    stage.show();
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/MainUser.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Welcome User");
                stage.show();
            }
           
          
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    } catch (Exception e) {
        System.out.println("An error occurred during login:");
        e.printStackTrace();
    }
}


    @FXML
    private void signClick(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/SignIn.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign Up Page");
        stage.show();
    }
}
