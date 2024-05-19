package com.itavgur.fx.filecopy.handler;

import com.itavgur.fx.filecopy.config.PropertiesHolder;
import com.itavgur.fx.filecopy.elements.CopyScene;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.NonNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CopyButtonHandler implements EventHandler<ActionEvent> {

    @NonNull
    private final CopyScene copyScene;

    private final PropertiesHolder properties = PropertiesHolder.getInstance();

    public CopyButtonHandler(@NonNull CopyScene scene) {
        this.copyScene = scene;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        if (copyScene.getTask() != null && copyScene.getTask().isRunning()) return;

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Path source = getDir(properties.getSourceFile());
                Path destination = getDir(properties.getDestinationFile());

                checkFileExist(source);

                try (InputStream in = Files.newInputStream(source);
                     OutputStream out = Files.newOutputStream(destination, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                    byte[] buffer = new byte[1024];
                    long totalBytes = Files.size(source);
                    long bytesCopied = 0;
                    int bytesRead;

                    while ((bytesRead = in.read(buffer)) != -1) {
                        if (isCancelled()) {
                            Files.deleteIfExists(destination);
                            updateProgress(0, totalBytes);
                            return null;
                        }
                        out.write(buffer, 0, bytesRead);
                        bytesCopied += bytesRead;
                        updateProgress(bytesCopied, totalBytes);
                    }
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                copyScene.getCancelButton().setDisable(true);
                copyScene.getInfoMessage().setText("The file has been copied");
                copyScene.getInfoMessage().setStyle("-fx-fill: green;");
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                resetUI();
            }

            @Override
            protected void failed() {
                super.failed();
                resetUI();
            }
        };
        copyScene.setTask(task);
        copyScene.getProgressBar().progressProperty().bind(task.progressProperty());
        copyScene.getCopyButton().setDisable(true);
        copyScene.getCancelButton().setDisable(false);
        copyScene.getProgressBar().setVisible(true);
        new Thread(task).start();

    }

    private Path getDir(String path) {

        if (path.trim().charAt(0) == '~') {
            return Paths.get(System.getProperty("user.home") + path.substring(1));
        } else {
            return Paths.get(path);
        }

    }

    private void checkFileExist(Path file) {

        if (!Files.exists(file)) {
            copyScene.getInfoMessage().setText("file doesn't exist");
            copyScene.getInfoMessage().setStyle("-fx-fill: red;");
        }

    }

    private void resetUI() {
        copyScene.getCopyButton().setDisable(false);
        copyScene.getCancelButton().setDisable(true);
        copyScene.getProgressBar().progressProperty().unbind();
        copyScene.getProgressBar().setProgress(0);
        copyScene.getProgressBar().setVisible(false);
    }
}