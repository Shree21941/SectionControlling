// 1. javac --module-path "C:\JavaFX\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml -d ../bin RailwaySimulation.java Train.java Station.java
// 2. java --module-path "C:\JavaFX\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp ../bin RailwaySimulation


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import java.util.*;

public class RailwaySimulation extends Application {
    Station A = new Station("A", 3);  // Station A with 3 platforms
    TextArea logArea = new TextArea();
    VBox platformBox = new VBox(5);
    VBox queueBox = new VBox(5);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Railway Section Controller");

        Label title = new Label("Station A - Passenger & Freight Traffic Manager");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        logArea.setEditable(false);
        logArea.setPrefHeight(150);

        updatePlatformBox();
        updateQueueBox();

        TextField trainIdField = new TextField();
        trainIdField.setPromptText("Train ID");
        TextField priorityField = new TextField();
        priorityField.setPromptText("Priority (int)");

        Button addTrainButton = new Button("Add Train to Station A");
        addTrainButton.setOnAction(e -> {
            String id = trainIdField.getText();
            int prio;
            try {
                prio = Integer.parseInt(priorityField.getText());
            } catch (NumberFormatException ex) {
                log("Invalid priority. Enter an integer.");
                return;
            }
            Train train = new Train(id, null, null, prio);
            train.moveTo(A);
            updatePlatformBox();
            updateQueueBox();
            log("Train " + id + " attempted to arrive.");
            trainIdField.clear();
            priorityField.clear();
        });

        Button releaseTrainButton = new Button("Release First Train");
        releaseTrainButton.setOnAction(e -> {
            if (!A.occupyingTrains.isEmpty()) {
                Train t = A.occupyingTrains.get(0);
                A.releaseTrain(t);
                updatePlatformBox();
                updateQueueBox();
                log("Train " + t.trainId + " released from Station A.");
            } else {
                log("No train to release.");
            }
        });

        HBox inputRow = new HBox(10, trainIdField, priorityField, addTrainButton, releaseTrainButton);
        inputRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, title, inputRow,
                new Label("Platforms:"), platformBox,
                new Label("Waiting Queue:"), queueBox,
                new Label("Log:"), logArea);
        root.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }

    private void updatePlatformBox() {
        platformBox.getChildren().clear();
        for (Train t : A.occupyingTrains) {
            platformBox.getChildren().add(new Label("Train " + t.trainId + " (Priority: " + t.priority + ")"));
        }
        for (int i = A.occupyingTrains.size(); i < A.platformCount; i++) {
            platformBox.getChildren().add(new Label("[Empty Platform]"));
        }
    }

    private void updateQueueBox() {
        queueBox.getChildren().clear();
        for (Train t : A.waitingQueue) {
            queueBox.getChildren().add(new Label("Train " + t.trainId + " (Priority: " + t.priority + ")"));
        }
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
