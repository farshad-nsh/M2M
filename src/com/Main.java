package com;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Created by Farshad Noravesh on 4/24/17.
 */
public class Main extends Application{
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Login Control");
        stage.setWidth(800);
        stage.setHeight(600);
        LoginControl loginControl = new LoginControl(stage);
    }
}



