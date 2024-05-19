package com.itavgur.fx.filecopy.elements;

import com.itavgur.fx.filecopy.config.PropertiesHolder;
import com.itavgur.fx.filecopy.handler.CancelButtonHandler;
import com.itavgur.fx.filecopy.handler.CopyButtonHandler;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CopyScene {

    private static final double SCENE_WIDTH = 500;
    private static final double SCENE_HEIGHT = 200;
    private final PropertiesHolder properties = PropertiesHolder.getInstance();
    private Scene scene;
    private Button copyButton;
    private Button cancelButton;
    private ProgressBar progressBar;
    private Text infoMessage;
    @Setter
    private Task<Void> task;

    public CopyScene() {

        initVBox();
    }

    private void initVBox() {

        Label from = new Label("Source: " + properties.getSourceFile());
        Label to = new Label("Destination: " + properties.getDestinationFile());
        from.setWrapText(true);
        from.setMaxWidth(SCENE_WIDTH - 50);
        to.setWrapText(false);
        to.setMaxWidth(SCENE_WIDTH - 50);

        VBox filesInfoBox = new VBox(10);
        filesInfoBox.setPadding(new Insets(10));
        filesInfoBox.getChildren().addAll(new TextFlow(from), new TextFlow(to));

        copyButton = new Button("Copy");
        copyButton.setOnAction(new CopyButtonHandler(this));

        cancelButton = new Button("Cancel");
        cancelButton.setDisable(true);
        cancelButton.setOnAction(new CancelButtonHandler(this));

        HBox buttonsBox = new HBox(20);
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.getChildren().addAll(copyButton, cancelButton);

        progressBar = new ProgressBar(0);
        progressBar.setMinWidth(SCENE_WIDTH - 100);
        progressBar.setVisible(false);

        infoMessage = new Text();

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(filesInfoBox, buttonsBox, progressBar, infoMessage);

        scene = new Scene(vbox, SCENE_WIDTH, SCENE_HEIGHT);
    }

}