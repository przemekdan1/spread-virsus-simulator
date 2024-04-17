package com.example.simulation;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationVisualization extends Application {
    private Simulation simulation;
    private Caretaker caretaker;
    private Pane root;
    private Rectangle visibleRectangle;
    private Rectangle vRectangle;
    private VBox buttonsLayout;
    private VBox stateList;
    private ListView<Memento> savedStatesList;
    private  boolean restoreRequested = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Infection simulation");
        root = new Pane();


        this.simulation = new Simulation();
        this.caretaker = new Caretaker();

        visibleRectangle = new Rectangle(0, 0, simulation.getArea().getN() + 10 , simulation.getArea().getM() + 10);
        visibleRectangle.setStroke(Color.BLACK);
        visibleRectangle.setFill(Color.LIGHTGREEN);


        root.getChildren().add(visibleRectangle);

        Button saveStateButton = new Button("Zapisz stan");
        Button deleteStatesButton = new Button("Usuń stany");

        saveStateButton.setStyle("-fx-font-size: 20;");
        deleteStatesButton.setStyle("-fx-font-size: 20;");

        String buttonStyle = "-fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 20px; " +
                "-fx-font-weight: bold;"+
                "-fx-border-radius: 6px;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 6, 0, 3, 3);";

        saveStateButton.setStyle(buttonStyle);
        deleteStatesButton.setStyle(buttonStyle);



        // Obsługa przycisku "Zapisz stan"
        saveStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Wywołaj metodę obsługującą zapisywanie stanu (możesz ją zaimplementować)
                saveState();
            }
        });

        // Obsługa przycisku "Usuń zapisane stany"
        deleteStatesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Wywołaj metodę obsługującą usuwanie zapisanych stanów (możesz ją zaimplementować)
                deleteStates();
            }
        });

        savedStatesList = new ListView<>();
        savedStatesList.setPrefWidth(150);
        savedStatesList.setPrefHeight(155);
        savedStatesList.setItems(caretaker.getMementos()); // Ustaw elementy listy na zapisane pamiątki
        savedStatesList.setCellFactory(param -> new ListCell<Memento>() {
            @Override
            protected void updateItem(Memento item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Stan " + (getIndex()+1) ); // Numeracja zapisanych stanów
                }
            }
        });

        savedStatesList.setOnMouseClicked(event -> {
            Memento selectedMemento = savedStatesList.getSelectionModel().getSelectedItem();
            if (selectedMemento != null) {

                restoreRequested = true;

                simulation.getPopulation().setMemento(selectedMemento);  // Ustawienie pamiątki w Population
                List<Person> restoredState = simulation.getPopulation().getState();  // Pobranie stanu z Population
                simulation.getPopulation().setState(restoredState);  // Ustawienie stanu w Population
                updatePopulationNodes();
                System.out.println("Przywrócono zapisany stan!");

                restoreRequested = false;
            }
        });


        buttonsLayout = new VBox(saveStateButton);
        buttonsLayout.setSpacing(60);
        root.getChildren().add(buttonsLayout);

        buttonsLayout.setLayoutY(visibleRectangle.getWidth() + 50); // 10 to odstęp
        buttonsLayout.setLayoutX((int)simulation.getmLength() * 0.2);

        stateList = new VBox(savedStatesList);
        stateList.setStyle("-fx-font-size: 20;");
        String listStyle = "-fx-font-size: 16px; " +
                           "-fx-font-weight: bold;";

        stateList.setStyle(listStyle);
        root.getChildren().add(stateList);
        stateList.setLayoutY(visibleRectangle.getWidth() + 50); // 10 to odstęp
        stateList.setLayoutX((int)simulation.getmLength() * 0.5);

        Scene scene = new Scene(root, simulation.getArea().getN() , simulation.getArea().getM()+ 300);
        primaryStage.setScene(scene);

        // Add population nodes to the scene
        for (Person person : simulation.getPopulation().getPeople()) {
            root.getChildren().add(new PersonNode(person));
        }

        primaryStage.show();

        // Start the simulation loop
        startSimulationLoop();
    }

    private void startSimulationLoop() {
        new AnimationTimer() {
            private long lastUpdateTime = 0;
            private final long targetFrameTime = 40_000_000; // 40 milliseconds in nanoseconds

            @Override
            public void handle(long now) {
                if (now - lastUpdateTime >= targetFrameTime) {
                    // Update the simulation at each frame

                    if (!restoreRequested) {
                        simulation.performTimeStep(lastUpdateTime);
                        updatePopulationNodes();
                    }

                    lastUpdateTime = now;
                }
                checkButtonPress();
            }
        }.start();
    }

    private void updatePopulationNodes() {
        // Clear existing nodes and add updated nodes based on the current state of the population
        root.getChildren().removeIf(node -> node instanceof PersonNode);

        // Add population nodes to the scene
        for (Person person : simulation.getPopulation().getPeople()) {
            root.getChildren().add(new PersonNode(person));
        }
    }

    // Metoda do sprawdzania, czy został naciśnięty przycisk
    private void checkButtonPress() {
        // Pobierz listę obszarów zdarzeń (event areas), które zostały wykryte w danym momencie
        List<EventType> eventTypes = new ArrayList<>();
        root.addEventFilter(ActionEvent.ANY, event -> eventTypes.add(event.getEventType()));

        // Sprawdź, czy wśród obszarów zdarzeń jest zdarzenie od przycisku "Zapisz stan"
        if (eventTypes.contains(ActionEvent.ACTION)) {
            // Wywołaj metodę obsługującą przycisk "Zapisz stan"
            Platform.runLater(() -> saveState());
        }

        // Sprawdź, czy wśród obszarów zdarzeń jest zdarzenie od przycisku "Usuń zapisane stany"
        if (eventTypes.contains(ActionEvent.ACTION)) {
            // Wywołaj metodę obsługującą przycisk "Usuń zapisane stany"
            Platform.runLater(() -> deleteStates());
        }

        // Wyczyść listę obszarów zdarzeń
        eventTypes.clear();
    }

    // Metoda do obsługi przycisku "Zapisz stan"
    private void saveState() {
        List<Person> currentState = simulation.getPopulation().getPeople();
        simulation.getPopulation().setState(currentState);
        caretaker.addMemento(new Memento(currentState));
        savedStatesList.setItems(caretaker.getMementos()); // Zaktualizuj listę zapisanych stanów
        System.out.println("Zapisano stan!");
    }

    // Metoda do obsługi przycisku "Usuń zapisane stany"
    private void deleteStates() {
        caretaker.clearMementos(); // Usuń wszystkie zapisane stany
        savedStatesList.getItems().clear(); // Wyczyść listę zapisanych stanów w interfejsie
        System.out.println("Usunięto wszystkie zapisane stany!");
    }
}