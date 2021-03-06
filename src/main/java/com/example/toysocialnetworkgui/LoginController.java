package com.example.toysocialnetworkgui;
import com.example.toysocialnetworkgui.domain.User;
import com.example.toysocialnetworkgui.repository.repoExceptions.RepoException;
import com.example.toysocialnetworkgui.service.SuperService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    SuperService superService;

    @FXML
    Label usernameLabel;
    @FXML
    Label passwordLabel;
    @FXML
    Label noAccountLabel;
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;


    public void setServiceController(SuperService serviceController) {
        this.superService = serviceController;
    }

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {

        String username = usernameTextField.getText();
        String password = HashPassword.passHashing(passwordTextField.getText());
        if(username.equals("")){
            return;
        }
            try {
                boolean found = superService.login(username, password);
                if (found == false) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("This user doesn't exist!\n");
                    alert.showAndWait();
                    return;
                }
                Node source = (Node) event.getSource();
                Stage current = (Stage) source.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main2-view.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, 900, 600);
                current.setTitle("Pixel " + username);
                current.setScene(scene);
                Main2Controller ctrl = fxmlLoader.getController();

                ctrl.afterLoad(superService,superService.findUsersByUsernameAndPassword(username,password));
                ctrl.startThread();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    public void onRegisterButtonClick(ActionEvent event) {

       try {
           Node source = (Node) event.getSource();
           Stage current = (Stage) source.getScene().getWindow();
           FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("signup-view.fxml"));
           Parent root = fxmlLoader.load();
           SignupController signupController = fxmlLoader.getController();
           signupController.setSuperService(superService);
           Scene scene = new Scene(root, 900, 600);
           current.setTitle("Pixel");
           current.setScene(scene);

       }catch (IOException e) {
           e.printStackTrace();
       }
    }

}
