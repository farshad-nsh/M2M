package com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Toggle;

import java.io.IOException;
import java.net.URL;

import javafx.beans.value.ObservableValue;

/**
 * Created by Farshad Noravesh on 4/25/17.
 */

public class SettingControl extends AnchorPane {
    public String username,password;
    public String server;
    public Stage settingStage;
    @FXML
    TextField fxServerPort;

    @FXML ToggleGroup selectOS;
    @FXML RadioButton linux,windows,mac;
    @FXML
    AnchorPane anchorPanePopup;
    public SettingControl(Stage stage,String username,String password) throws IOException {
    this.username=username;
    this.password=password;
      //  this.server="4201";
        System.out.println("username in SettingControl is:"+username);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "settingControl.fxml"));


        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            settingStage=stage;
            settingStage.setScene(new Scene(this));
            settingStage.setTitle("setting Control");
            settingStage.setWidth(900);
            settingStage.setHeight(600);
            settingStage.show();
                selectOS.selectedToggleProperty().addListener(
                        (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                            if (selectOS.getSelectedToggle() != null) {
                               if(selectOS.getSelectedToggle().selectedProperty().getBean().toString()
                                       .compareTo("RadioButton[id=linux, styleClass=radio-button]'linux'")==0){
                                   System.out.println("linux");
                                   try {
                                       server=fxServerPort.getCharacters().toString();
                                       URL u=getClass().getResource("usbPortSetting.fxml");
                                       SerialPortReader controller = new SerialPortReader(this.username,"linux",this.server,this.password);
                                       FXMLLoader Linux = new FXMLLoader(u);
                                       Linux.setRoot(controller);
                                       Linux.setController(controller);
                                       Linux.load();
                                       stage.setScene(new Scene(controller));
                                       stage.setTitle("Linux Welcome");
                                       stage.show();
                                   } catch (IOException e) {
                                       e.printStackTrace();
                                   }
                               }else if(selectOS.getSelectedToggle().selectedProperty().getBean().toString()
                                       .compareTo("RadioButton[id=mac, styleClass=radio-button]'mac'")==0)
                                {
                                    System.out.println("mac");
                                    try {
                                        server=fxServerPort.getCharacters().toString();
                                        URL u=getClass().getResource("usbPortSetting.fxml");
                                        SerialPortReader controller = new SerialPortReader(this.username,"mac",this.server,this.password);
                                        FXMLLoader Linux = new FXMLLoader(u);
                                        Linux.setRoot(controller);
                                        Linux.setController(controller);
                                        Linux.load();
                                        stage.setScene(new Scene(controller));
                                        stage.setTitle("Mac Welcome");
                                        stage.show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                       // stage.setScene(new Scene(this, 700, 400));
                                      //  stage.show();
                                }else if(selectOS.getSelectedToggle().selectedProperty().getBean().toString()
                                       .compareTo("RadioButton[id=windows, styleClass=radio-button]'windows'")==0)
                               {
                                   System.out.println("windows");

                               }


                            }
                        }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










}
