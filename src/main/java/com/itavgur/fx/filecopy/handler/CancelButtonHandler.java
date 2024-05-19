package com.itavgur.fx.filecopy.handler;

import com.itavgur.fx.filecopy.elements.CopyScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.NonNull;

public class CancelButtonHandler implements EventHandler<ActionEvent> {

    @NonNull
    private final CopyScene copyScene;

    public CancelButtonHandler(@NonNull CopyScene copyScene) {
        this.copyScene = copyScene;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        if (copyScene.getTask().isRunning()) copyScene.getTask().cancel();

    }

}