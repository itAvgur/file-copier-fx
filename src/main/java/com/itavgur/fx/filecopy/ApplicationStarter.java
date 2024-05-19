package com.itavgur.fx.filecopy;

import com.itavgur.fx.filecopy.elements.CopyScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationStarter extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {

        CopyScene copyScene = new CopyScene();
        stage.setScene(copyScene.getScene());
        stage.show();
    }
}