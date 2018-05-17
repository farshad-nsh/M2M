package com;

/**
 * Created by Farshad Noravesh on 4/24/17.
 */
import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class LoginControl extends VBox {
    @FXML private TextField username;
    @FXML private TextField password;
    public Stage loginStage;
    public LoginControl(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "login.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            stage.setScene(new Scene(this));
            loginStage=stage;
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getUsername() {
        return username.textProperty().get();
    }
    public String getPassword() {
        return password.textProperty().get();
    }


    public StringProperty usernameProperty() {
        return username.textProperty();
    }
    public StringProperty passwordProperty() {
        return password.textProperty();
    }

    @FXML
    protected void login()
    {
        System.out.println("logging......!");
        System.out.println("username="+username.textProperty().get());
        System.out.println("password="+password.textProperty().get());
        loginStage.close();
        Stage customStage=new Stage();
        CustomControl customControl = new CustomControl(customStage,getUsername(),getPassword());
        //customControl.setText("Hello!");

    }
}
