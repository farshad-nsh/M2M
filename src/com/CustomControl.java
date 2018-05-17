package com;

/**
 * Created by Farshad Noravesh on 4/24/17.
 */
import java.io.IOException;

import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;


public class CustomControl extends AnchorPane {
    @FXML private TextField textField;
    public Stage customStage;
    public String username,password;
    public CustomControl(Stage stage,String username,String password) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "custom_control.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        customStage=stage;
        this.username=username;
        this.password=password;
        try {
            fxmlLoader.load();
            customStage.setScene(new Scene(this));
            customStage.setTitle("Custom Control");
            customStage.setWidth(900);
            customStage.setHeight(600);
            customStage.show();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty textProperty() {
        return textField.textProperty();
    }

    @FXML
    protected void doSomething() {
        System.out.println("The button was clicked!");
    }
    @FXML
    protected void goToPortSettings(){
        System.out.println("cool");
        customStage.close();
        Stage settingStage=new Stage();
        try {

            SettingControl settingControl = new SettingControl(settingStage,username,password);
            System.out.println("username in customControl:"+username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
